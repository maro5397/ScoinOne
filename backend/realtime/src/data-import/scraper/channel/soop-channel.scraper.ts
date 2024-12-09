import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class SoopChannelScraper {
  constructor(private readonly httpService: HttpService) {}

  async getFollowerCount(streamerId: string) {
    const url = `https://chapi.sooplive.co.kr/api/${streamerId}/station`;

    const config = {
      headers: {
        'User-Agent':
          'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
      },
    };

    try {
      const response = await firstValueFrom(this.httpService.get(url, config));
      const followerCount = response.data.station.upd.fan_cnt;
      return { followerCount: followerCount };
    } catch (error) {
      throw new Error(error);
    }
  }
}
