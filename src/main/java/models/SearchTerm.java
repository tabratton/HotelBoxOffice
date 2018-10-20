package models;

public class SearchTerm {
  private String searchTermId;
  private String term;
  private int freq;

  public SearchTerm() {
    this.searchTermId = "";
    this.term = "";
    this.freq = 0;
  }

  public SearchTerm(String searchTermId, String term, int freq) {
    this.searchTermId = searchTermId;
    this.term = term;
    this.freq = freq;
  }

  public String getSearchTermId() {
    return searchTermId;
  }

  public void setSearchTermId(String searchTermId) {
    this.searchTermId = searchTermId;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  public Integer getFreq() {
    return freq;
  }

  public void setFreq(int freq) {
    this.freq = freq;
  }
}
