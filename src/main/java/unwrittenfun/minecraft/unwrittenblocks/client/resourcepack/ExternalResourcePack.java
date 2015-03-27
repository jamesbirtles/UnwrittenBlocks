package unwrittenfun.minecraft.unwrittenblocks.client.resourcepack;

import net.minecraft.client.resources.FolderResourcePack;

import java.io.File;

/**
 * Author: James Birtles
 */
public class ExternalResourcePack extends FolderResourcePack {
  public String name;
  public ExternalResourcePack(String name, File folder) {
    super(folder);
    this.name = name;
  }

  @Override
  public String getPackName() {
    return name;
  }
}
