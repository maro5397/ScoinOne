import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import WebSocket from 'ws';

@Injectable()
export class ChzzkLiveChatScraper {
  private socket: WebSocket | null = null;

  constructor(private readonly httpService: HttpService) {}

  async getLiveChats(streamerId: string) {
    const chatUrls: string[] = [
      'wss://kr-ss1.chat.naver.com/chat',
      'wss://kr-ss2.chat.naver.com/chat',
      'wss://kr-ss3.chat.naver.com/chat',
      'wss://kr-ss4.chat.naver.com/chat',
      'wss://kr-ss5.chat.naver.com/chat',
    ];

    const channelId = await this.getChannelId(streamerId);
    if (channelId === null) {
      throw new Error('Streamer is not Streaming now');
    }
    const accessToken = await this.getAccessToken(channelId);

    this.socket = new WebSocket(chatUrls[4]);
    this.socket.on('open', () => {
      console.log('Connected to chat server');
      this.initializeChatConnection(channelId, accessToken);
    });

    this.socket.on('message', (data: string) => {
      this.handleMessage(data);
    });

    this.socket.on('error', (error) => {
      console.error('WebSocket error:', error);
    });

    this.socket.on('close', (code, reason) => {
      console.log('WebSocket closed:', code, reason.toString());
    });

    return { channelId, accessToken };
  }

  private initializeChatConnection(channelId: string, accessToken: string) {
    if (!this.socket) return;
    const connectPayload = {
      ver: '3',
      cmd: 100,
      svcid: 'game',
      cid: channelId,
      bdy: {
        uid: null,
        devType: 2001,
        accTkn: accessToken,
        auth: 'READ',
        libVer: '4.9.3',
        osVer: 'Windows/10',
        devName: 'Google Chrome/131.0.0.0',
        locale: 'ko-KR',
        timezone: 'Asia/Seoul',
      },
      tid: 1,
    };
    this.socket.send(JSON.stringify(connectPayload));
  }

  private handleMessage(data: string) {
    const message = JSON.parse(data);
    console.log('Received message:', message);

    if (message.cmd === 93101) {
      console.log(message);
    } else if (message.cmd === 10000) {
      this.sendPing();
    }
  }

  private sendPing() {
    if (!this.socket) return;
    const pingPayload = {
      ver: '3',
      cmd: 0,
    };
    this.socket.send(JSON.stringify(pingPayload));
  }

  private async getAccessToken(channelId: string) {
    const chatAccessTokenUrl = `https://comm-api.game.naver.com/nng_main/v1/chats/access-token?channelId=${channelId}&chatType=STREAMING`;
    const config = {
      headers: {
        'User-Agent':
          'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
      },
    };

    try {
      const response = await firstValueFrom(this.httpService.get(chatAccessTokenUrl, config));
      return response.data.content.accessToken;
    } catch (error) {
      throw error;
    }
  }

  private async getChannelId(streamerId: string) {
    const statusUrl = `https://api.chzzk.naver.com/polling/v3/channels/${streamerId}/live-status`;
    const config = {
      headers: {
        'User-Agent':
          'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
      },
    };

    try {
      const response = await firstValueFrom(this.httpService.get(statusUrl, config));
      if (response.data.content.status === 'OPEN') {
        return response.data.content.chatChannelId;
      }
      return null;
    } catch (error) {
      throw error;
    }
  }
}
