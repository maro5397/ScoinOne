import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class ChzzkChannelScraper {
  constructor(private readonly httpService: HttpService) {}

  async getFollowerCount(streamerId: string) {
    const url = `https://api.chzzk.naver.com/service/v1/channels/${streamerId}`;
    const config = {
      headers: {
        'User-Agent':
          'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
      },
    };

    try {
      const response = await firstValueFrom(this.httpService.get(url, config));
      const followerCount = response.data.content.followerCount;
      return { followerCount: followerCount };
    } catch (error) {
      throw error;
    }
  }
}
