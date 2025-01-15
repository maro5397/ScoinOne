import { ChzzkLiveChatScraper } from '../../../../../scraper/live/chzzk/chzzk-live-chat.scraper';

(async function () {
  const scraper = new ChzzkLiveChatScraper();

  try {
    const streamerId = '75cbf189b3bb8f9f687d2aca0d0a382b';
    await scraper.getLiveChats(streamerId);
  } catch (error) {
    console.error(error.message);
  }
})();
