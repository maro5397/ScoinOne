import { ChzzkChannelScraper } from '../../../../scraper/channel/chzzk-channel.scraper';
import { TestingModule, Test } from '@nestjs/testing';
import { HttpModule } from '@nestjs/axios';

const HANDONGSUK_ID = '75cbf189b3bb8f9f687d2aca0d0a382b';

describe('ChzzkChannelScraperTest', () => {
  let scraper: ChzzkChannelScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        HttpModule.register({
          timeout: 5000,
          maxRedirects: 5,
        }),
      ],
      providers: [ChzzkChannelScraper],
    }).compile();

    scraper = module.get<ChzzkChannelScraper>(ChzzkChannelScraper);
  });

  it('should return follower count', async () => {
    const result = await scraper.getFollowerCount(HANDONGSUK_ID);
    expect(result.followerCount).toBeDefined();
    expect(typeof result.followerCount).toBe('number');
  });
});
