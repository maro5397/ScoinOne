import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class YoutubeChannelScrape {
  private readonly API_KEY: string;

  constructor(
    private readonly configService: ConfigService,
    private readonly httpService: HttpService,
  ) {
    this.API_KEY = this.configService.get<string>('YOUTUBE_API_KEY');
  }

  async getChannelStatistics(channelId: string) {
    const url = `https://www.googleapis.com/youtube/v3/channels?part=statistics&id=${channelId}&key=${this.API_KEY}`;

    try {
      const response = await firstValueFrom(this.httpService.get(url));
      const statistics = response.data.items[0].statistics;
      return { subscriberCount: statistics.subscriberCount, viewCount: statistics.viewCount };
    } catch (error) {
      console.error('Error fetching channel stats:', error);
      throw new Error('Failed to fetch channel stats');
    }
  }
}
