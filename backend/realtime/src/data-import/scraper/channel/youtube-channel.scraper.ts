import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class YoutubeChannelScraper {
  private readonly YOUTUBE_API_KEY: string;

  constructor(
    private readonly configService: ConfigService,
    private readonly httpService: HttpService,
  ) {
    this.YOUTUBE_API_KEY = this.configService.get<string>('YOUTUBE_API_KEY');
  }

  async getChannelStatistics(channelId: string) {
    const url = `https://www.googleapis.com/youtube/v3/channels?part=statistics&forHandle=${channelId}&key=${this.YOUTUBE_API_KEY}`;

    try {
      const response = await firstValueFrom(this.httpService.get(url));
      const statistics = response.data.items[0].statistics;
      if (response.data.items.length > 1) {
        throw new Error('Exploring duplicate users');
      }
      if (response.data.items.length === 0) {
        throw new Error('There is no user');
      }
      return { subscriberCount: Number(statistics.subscriberCount), viewCount: Number(statistics.viewCount) };
    } catch (error) {
      throw new Error(error);
    }
  }
}
