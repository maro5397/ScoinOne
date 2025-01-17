import { Injectable } from '@nestjs/common';
import { SoopClient } from './client';

@Injectable()
export class SoopLiveChatScraper {
  // 19금 설정 방송의 경우 채팅 가져오기 불가 (테스트 필요)
  async getLiveChats(streamerId: string) {
    const client = new SoopClient();

    const soopChat = client.chat({
      streamerId: streamerId,
    });

    // 연결 성공
    soopChat.on('connect', (response) => {
      console.log(`[${response.receivedTime}] Connected to ${response.streamerId}`);
    });

    // 채팅방 입장
    soopChat.on('enterChatRoom', (response) => {
      console.log(`[${response.receivedTime}] Enter to ${response.streamerId}'s chat room`);
    });

    // 일반 채팅
    soopChat.on('chat', (response) => {
      console.log(`[${response.receivedTime}] ${response.username}(${response.userId}): ${response.comment}`);
    });

    // 이모티콘 채팅
    soopChat.on('emoticon', (response) => {
      console.log(`[${response.receivedTime}] ${response.username}(${response.userId}): ${response.emoticonId}`);
    });

    // 텍스트/음성 후원 채팅
    soopChat.on('textDonation', (response) => {
      console.log(
        `\n[${response.receivedTime}] ${response.fromUsername}(${response.from})님이 ${response.to}님에게 ${response.amount}개 후원`,
      );
      if (Number(response.fanClubOrdinal) !== 0) {
        console.log(`[${response.receivedTime}] ${response.fanClubOrdinal}번째 팬클럽 가입을 환영합니다.\n`);
      } else {
        console.log(`[${response.receivedTime}] 이미 팬클럽에 가입된 사용자입니다.\n`);
      }
    });

    // 영상 후원 채팅
    soopChat.on('videoDonation', (response) => {
      console.log(
        `\n[${response.receivedTime}] ${response.fromUsername}(${response.from})님이 ${response.to}님에게 ${response.amount}개 후원`,
      );
      if (Number(response.fanClubOrdinal) !== 0) {
        console.log(`[${response.receivedTime}] ${response.fanClubOrdinal}번째 팬클럽 가입을 환영합니다.\n`);
      } else {
        console.log(`[${response.receivedTime}] 이미 팬클럽에 가입된 사용자입니다.\n`);
      }
    });

    // 애드벌룬 후원 채팅
    soopChat.on('adBalloonDonation', (response) => {
      console.log(
        `\n[${response.receivedTime}] ${response.fromUsername}(${response.from})님이 ${response.to}님에게 ${response.amount}개 후원`,
      );
      if (Number(response.fanClubOrdinal) !== 0) {
        console.log(`[${response.receivedTime}] ${response.fanClubOrdinal}번째 팬클럽 가입을 환영합니다.\n`);
      } else {
        console.log(`[${response.receivedTime}] 이미 팬클럽에 가입된 사용자입니다.\n`);
      }
    });

    // 방송 종료
    soopChat.on('disconnect', (response) => {
      console.log(`[${response.receivedTime}] ${response.streamerId}의 방송이 종료됨`);
    });

    await soopChat.connect();
  }
}
