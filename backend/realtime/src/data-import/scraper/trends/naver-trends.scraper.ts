import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';

@Injectable()
export class NaverTrendsScraper {
  private readonly clientId: string;
  private readonly clientSecret: string;

  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService,
  ) {
    this.clientId = this.configService.get<string>('NAVER_CLIENT_ID');
    this.clientSecret = this.configService.get<string>('NAVER_CLIENT_SECRET');
  }

  async getTrends(keyword: string) {
    const url = `https://openapi.naver.com/v1/datalab/search`;
    const headers = {
      'X-Naver-Client-Id': this.clientId,
      'X-Naver-Client-Secret': this.clientSecret,
      'Content-Type': 'application/json',
    };

    const today = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);
    const dayBeforeYesterday = new Date(today);
    dayBeforeYesterday.setDate(today.getDate() - 2);

    const body = {
      startDate: this.formatDate(dayBeforeYesterday),
      endDate: this.formatDate(yesterday),
      timeUnit: 'date',
      keywordGroups: [
        {
          groupName: keyword,
          keywords: [keyword],
        },
      ],
    };

    try {
      const response = await firstValueFrom(this.httpService.post(url, body, { headers }));
      return response.data.results[0]?.data;
    } catch (error) {
      console.error('Error fetching trends:', error);
      throw new Error('Failed to fetch trends');
    }
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }
}
