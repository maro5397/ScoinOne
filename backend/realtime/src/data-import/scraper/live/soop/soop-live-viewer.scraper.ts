import { Injectable } from '@nestjs/common';
import { SoopClient } from './client';

@Injectable()
export class SoopLiveViewerScraper {
  async getViewerCount(streamerId: string) {
    try {
      const client = new SoopClient();
      const station = await client.channel.station(streamerId);
      if (!station.broad) {
        return { live: false, viewerCount: -1 };
      }
      return { live: true, viewerCount: station.broad.current_sum_viewer };
    } catch (error) {
      throw error;
    }
  }
}
