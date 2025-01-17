import { TestingModule, Test } from '@nestjs/testing';
import { NaverCafeScraper } from '../../../../src/scraper/fandom/naver-cafe.scraper';

describe('NaverCafeScraperTest', () => {
  let scraper: NaverCafeScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [],
      providers: [NaverCafeScraper],
    }).compile();

    scraper = module.get<NaverCafeScraper>(NaverCafeScraper);
  });

  it('should return subscriber and view count', async () => {
    const result = await scraper.getIntroduceData('27842958');
    expect(result.memberCount).toBeDefined();
    expect(result.postCount).toBeDefined();
    expect(result.visitorCount).toBeDefined();
    expect(typeof result.memberCount).toBe('number');
    expect(typeof result.postCount).toBe('number');
    expect(typeof result.visitorCount).toBe('number');
  });
});
