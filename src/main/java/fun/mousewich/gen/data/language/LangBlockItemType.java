package fun.mousewich.gen.data.language;

public enum LangBlockItemType {
	BARREL(name -> name + " Barrel"),
	BOOKSHELF(name -> name + " Bookshelf");

	public final LanguageShortcut en_us;
	LangBlockItemType(LanguageShortcut en_us) { this.en_us = en_us; }
}
