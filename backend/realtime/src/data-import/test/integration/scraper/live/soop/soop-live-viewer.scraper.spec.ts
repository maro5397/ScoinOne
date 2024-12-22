import { SoopLiveViewerScraper } from '../../../../../scraper/live/soop/soop-live-viewer.scraper';

const STREAMER_ID = 'khm11903';

describe('SoopLiveViewerScraperTest', () => {
  const scraper: SoopLiveViewerScraper = new SoopLiveViewerScraper();

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
