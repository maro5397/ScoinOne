import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class ChzzkChannelScraper {
  constructor(private readonly httpService: HttpService) {}

  async getFollowerCount(streamerId: string) {
    const url = `https://api.chzzk.naver.com/service/v1/channels/${streamerId}`;

    try {
      const response = await firstValueFrom(this.httpService.get(url));
      const followerCount = response.data.content.followerCount;
      return { followerCount: followerCount };
    } catch (error) {
      console.error('Error fetching follower count:', error);
      throw new Error('Failed to fetch follower count');
    }
  }
}
