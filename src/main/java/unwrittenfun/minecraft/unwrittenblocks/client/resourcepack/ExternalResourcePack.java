package unwrittenfun.minecraft.unwrittenblocks.client.resourcepack;

import net.minecraft.client.resources.FolderResourcePack;

import java.io.File;

/**
 * Author: James Birtles
 */
public class ExternalResourcePack extends FolderResourcePack {
  public ExternalResourcePack(File folder) {
    super(folder);
  }

  @Override
  public String getPackName() {
    return "UnwrittenBlocks Storage Balls";
  }
}
