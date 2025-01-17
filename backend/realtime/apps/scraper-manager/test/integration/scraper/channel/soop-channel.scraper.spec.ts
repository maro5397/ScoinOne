import { TestingModule, Test } from '@nestjs/testing';
import { HttpModule } from '@nestjs/axios';
import { SoopChannelScraper } from '../../../../src/scraper/channel/soop-channel.scraper';

const WOOWAKGOOD_ID = 'ecvhao';

describe('SoopChannelScraperTest', () => {
  let scraper: SoopChannelScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        HttpModule.register({
          timeout: 5000,
          maxRedirects: 5,
        }),
      ],
      providers: [SoopChannelScraper],
    }).compile();

    scraper = module.get<SoopChannelScraper>(SoopChannelScraper);
  });

  it('should return follower count', async () => {
    const result = await scraper.getFollowerCount(WOOWAKGOOD_ID);
    expect(result.followerCount).toBeDefined();
    expect(typeof result.followerCount).toBe('number');
  });
});
