import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class ChzzkLiveViewerScraper {
  constructor(private readonly httpService: HttpService) {}

  async getViewerCount(streamerId: string) {
    const url = `https://api.chzzk.naver.com/polling/v3/channels/${streamerId}/live-status`;
    const config = {
      headers: {
        'User-Agent':
          'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
      },
    };

    try {
      const response = await firstValueFrom(this.httpService.get(url, config));
      if (response.data.content.status === 'OPEN') {
        const viewerCount = response.data.content.concurrentUserCount;
        return { live: true, viewerCount: viewerCount };
      }
      return { live: false, viewerCount: 0 };
    } catch (error) {
      throw error;
    }
  }
}
