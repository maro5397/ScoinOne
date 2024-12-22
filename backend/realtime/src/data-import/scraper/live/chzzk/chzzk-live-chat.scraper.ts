import { Injectable } from '@nestjs/common';
import { ChzzkClient } from './client';

@Injectable()
export class ChzzkLiveChatScraper {
  // 19금 설정 방송의 경우 채팅 가져오기 불가 (테스트 필요)
  async getLiveChats(streamerId: string) {
    const client = new ChzzkClient();

    const chzzkChat = client.chat({
      channelId: streamerId,
    });

    chzzkChat.on('connect', (chatChannelId) => {
      console.log(`Connected to ${chatChannelId}`);
      chzzkChat.requestRecentChat(50);
    });

    chzzkChat.on('reconnect', (chatChannelId) => {
      console.log(`Reconnected to ${chatChannelId}`);
    });

    chzzkChat.on('chat', (chat) => {
      const message = chat.hidden ? '[블라인드 처리 됨]' : chat.message;
      console.log(`${chat.profile.nickname}: ${message}`);
    });

    chzzkChat.on('donation', (donation) => {
      console.log(`\n>> ${donation.profile.nickname} 님이 ${donation.extras.payAmount}원 후원`);
      if (donation.message) {
        console.log(`>> ${donation.message}`);
      }
      console.log();
    });

    await chzzkChat.connect();
  }
}
