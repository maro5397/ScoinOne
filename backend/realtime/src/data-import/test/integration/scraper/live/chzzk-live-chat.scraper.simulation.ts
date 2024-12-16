import { HttpService } from '@nestjs/axios';
import { ChzzkLiveChatScraper } from '../../../../scraper/live/chzzk-live-chat.scraper';

(async function () {
  const httpService = new HttpService();

  const scraper = new ChzzkLiveChatScraper(httpService);

  try {
    const streamerId = 'a86031fdfeb1b223ad1172cf6ba4b94e';
    const liveChatInfo = await scraper.getLiveChats(streamerId);
    console.log('Live Chat Info:', liveChatInfo);
  } catch (error) {
    console.error('Error fetching live chat info:', error.message);
  }
})();
