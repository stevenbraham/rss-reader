package biz.braham.rssreader.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class RssReaderPromtProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("RSS-READER:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }
}
