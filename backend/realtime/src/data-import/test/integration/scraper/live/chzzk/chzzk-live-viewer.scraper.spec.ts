import { ChzzkLiveViewerScraper } from '../../../../../scraper/live/chzzk/chzzk-live-viewer.scraper';

const STREAMER_ID = '75cbf189b3bb8f9f687d2aca0d0a382b';

describe('ChzzkChannelScraperTest', () => {
  const scraper: ChzzkLiveViewerScraper = new ChzzkLiveViewerScraper();

  it('should return follower count', async () => {
    const result = await scraper.getViewerCount(STREAMER_ID);
    expect(result.live).toBeDefined();
    expect(typeof result.live).toBe('boolean');
    if (result.live) {
      expect(result.viewerCount).toBeDefined();
      expect(typeof result.viewerCount).toBe('number');
    }
  });
});
