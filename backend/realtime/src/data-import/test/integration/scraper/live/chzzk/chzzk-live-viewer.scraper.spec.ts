import { ChzzkLiveViewerScraper } from '../../../../../scraper/live/chzzk/chzzk-live-viewer.scraper';

const STREAMER_ID = 'a86031fdfeb1b223ad1172cf6ba4b94e';

describe('ChzzkLiveViewerScraperTest', () => {
  const scraper: ChzzkLiveViewerScraper = new ChzzkLiveViewerScraper();

  it('should return viewer count', async () => {
    const result = await scraper.getViewerCount(STREAMER_ID);
    expect(result.live).toBeDefined();
    expect(typeof result.live).toBe('boolean');
    if (result.live) {
      console.log(result.viewerCount);
      expect(result.viewerCount).toBeGreaterThanOrEqual(0);
      expect(result.viewerCount).toBeDefined();
      expect(typeof result.viewerCount).toBe('number');
    }
  });
});
