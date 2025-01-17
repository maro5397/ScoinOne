import { TestingModule, Test } from '@nestjs/testing';
import { HttpModule } from '@nestjs/axios';
import { YoutubeChannelScraper } from '../../../../src/scraper/channel/youtube-channel.scraper';
import { ConfigModule } from '@nestjs/config';

describe('YoutubeChannelScraperTest', () => {
  let scraper: YoutubeChannelScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        HttpModule.register({
          timeout: 5000,
          maxRedirects: 5,
        }),
        await ConfigModule.forRoot(),
      ],
      providers: [YoutubeChannelScraper],
    }).compile();

    scraper = module.get<YoutubeChannelScraper>(YoutubeChannelScraper);
  });

  it('should return subscriber and view count', async () => {
    const result = await scraper.getChannelStatistics('@woowakgood');
    expect(result.subscriberCount).toBeDefined();
    expect(result.viewCount).toBeDefined();
    expect(typeof result.subscriberCount).toBe('number');
    expect(typeof result.viewCount).toBe('number');
  });
});
