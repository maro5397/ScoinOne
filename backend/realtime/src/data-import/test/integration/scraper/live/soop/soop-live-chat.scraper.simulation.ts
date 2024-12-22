import { SoopLiveChatScraper } from '../../../../../scraper/live/soop/soop-live-chat.scraper';

(async function () {
  const scraper = new SoopLiveChatScraper();

  try {
    const streamerId = 'yeorumi030';
    await scraper.getLiveChats(streamerId);
  } catch (error) {
    console.error(error.message);
  }
})();
