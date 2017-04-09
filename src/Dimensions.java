public class Dimensions {
  public enum DimensionTypes {
    SMALL, MEDIUM, LARGE
  }

  private int width;
  private int height;
  private DimensionTypes currentType;

  /**
   * Default and only constructor.
   *
   * @param type A DimensionType, either SMALL, MEDIUM, or LARGE
   */
  public Dimensions(DimensionTypes type) {
    switch (type) {
      case SMALL:
        this.width = 100;
        this.height = 150;
        break;
      case MEDIUM:
        this.width = 150;
        this.height = 225;
        break;
      case LARGE:
        this.width = 300;
        this.height = 450;
        break;
      default:
        break;
    }
    this.currentType = type;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public DimensionTypes getCurrentType() {
    return this.currentType;
  }

  @Override
  public String toString() {
    switch (this.currentType) {
      case SMALL:
        return "small";
      case MEDIUM:
        return "medium";
      case LARGE:
        return "large";
      default:
        break;
    }
    return "";
  }
}
