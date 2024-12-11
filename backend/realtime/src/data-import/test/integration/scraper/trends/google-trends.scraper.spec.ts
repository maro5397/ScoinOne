import { TestingModule, Test } from '@nestjs/testing';
import { GoogleTrendsScraper } from '../../../../scraper/trends/google-trends.scraper';

describe('GoogleTrendsScraperTest', () => {
  let scraper: GoogleTrendsScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [],
      providers: [GoogleTrendsScraper],
    }).compile();

    scraper = module.get<GoogleTrendsScraper>(GoogleTrendsScraper);
  });

  it('should return subscriber and view count', async () => {
    const result = await scraper.getTrends('우왁굳');
    expect(result.length).toBe(2);
    expect(result[0].period).toBeDefined();
    expect(result[0].ratio).toBeDefined();
    expect(result[1].period).toBeDefined();
    expect(result[1].ratio).toBeDefined();
    if (result[0].period !== null && result[1].period !== null) {
      console.log('HTTP 200: Data');
      expect(typeof result[0].period).toBe('string');
      expect(typeof result[1].period).toBe('string');
      expect(typeof result[0].ratio).toBe('number');
      expect(typeof result[1].ratio).toBe('number');
    }
  }, 20000);
});
