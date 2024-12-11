import { TestingModule, Test } from '@nestjs/testing';
import { NaverTrendsScraper } from '../../../../scraper/trends/naver-trends.scraper';
import { HttpModule } from '@nestjs/axios';
import { ConfigModule } from '@nestjs/config';

describe('NaverTrendsScraperTest', () => {
  let scraper: NaverTrendsScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        HttpModule.register({
          timeout: 5000,
          maxRedirects: 5,
        }),
        await ConfigModule.forRoot(),
      ],
      providers: [NaverTrendsScraper],
    }).compile();

    scraper = module.get<NaverTrendsScraper>(NaverTrendsScraper);
  });

  it('should return subscriber and view count', async () => {
    const result = await scraper.getTrends('한동숙');
    expect(result.length).toBe(2);
    expect(typeof result[0].period).toBe('string');
    expect(typeof result[1].period).toBe('string');
    expect(typeof result[0].ratio).toBe('number');
    expect(typeof result[1].ratio).toBe('number');
  });
});
