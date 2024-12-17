import { TestingModule, Test } from '@nestjs/testing';
import { HttpModule } from '@nestjs/axios';
import { ChzzkLiveViewerScraper } from '../../../../../scraper/live/chzzk/chzzk-live-viewer.scraper';

const HANDONGSUK_ID = '75cbf189b3bb8f9f687d2aca0d0a382b';

describe('ChzzkChannelScraperTest', () => {
  let scraper: ChzzkLiveViewerScraper;

  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        HttpModule.register({
          timeout: 5000,
          maxRedirects: 5,
        }),
      ],
      providers: [ChzzkLiveViewerScraper],
    }).compile();

    scraper = module.get<ChzzkLiveViewerScraper>(ChzzkLiveViewerScraper);
  });

  it('should return follower count', async () => {
    const result = await scraper.getViewerCount(HANDONGSUK_ID);
    expect(result.live).toBeDefined();
    expect(typeof result.live).toBe('boolean');
    if (result.live) {
      expect(result.viewerCount).toBeDefined();
      expect(typeof result.viewerCount).toBe('number');
    }
  });
});
