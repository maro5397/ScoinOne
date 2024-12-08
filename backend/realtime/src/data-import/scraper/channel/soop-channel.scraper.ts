import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class SoopChannelScraper {
  constructor(private readonly httpService: HttpService) {}

  async getFollowerCount(streamerId: string) {
    const url = `https://chapi.sooplive.co.kr/api/${streamerId}/station`;

    try {
      const response = await firstValueFrom(this.httpService.get(url));
      const followerCount = response.data.station.upd.fan_cnt;
      return { followerCount };
    } catch (error) {
      console.error('Error fetching follower count:', error);
      throw new Error('Failed to fetch follower count');
    }
  }
}
