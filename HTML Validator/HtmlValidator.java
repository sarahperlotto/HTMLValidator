import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

/*
 * Implementation of edX SD2x Homework #2
 */

public class HtmlValidator {
	public static void main(String[] args) {
		try {
			Queue<HtmlTag> reviewTags = HtmlReader.getTagsFromHtmlFile("testhtml.txt");
			System.out.println("Tags for review: " + reviewTags.toString());
			Stack<HtmlTag> validatedTags = isValidHtml(reviewTags);
			if (validatedTags == null) {
				System.out.println("Null stack - invalid HTML.");
			}
			else if (!validatedTags.isEmpty()) {
				System.out.println("Non-empty stack - invalid HTML.");
				System.out.println(validatedTags.toString());
			}
			else {
				System.out.println("Empty stack - valid HTML.");
			}
		}
		catch (IOException exception) {
			System.out.println("IOException!");
		}
	}
	
	public static Stack<HtmlTag> isValidHtml(Queue<HtmlTag> tags) {
		Stack<HtmlTag> validatedTags = new Stack<>();
		if (tags == null || tags.isEmpty()) {
			//System.out.println("Queue of tags is null or empty - invalid.");
			return null;
		}
		if (!tags.peek().isOpenTag() && !tags.peek().isSelfClosing()) {
			//System.out.println("Queue of tags begins with non-open, non-self closing tag - invalid.");
			return null;
		}
		if (tags.size() == 1 && tags.peek().isSelfClosing()) { 
			//System.out.println("Queue of tags contains only 1 self-closing tag - valid.");
			return validatedTags;
		}
		for (HtmlTag current : tags) {
			System.out.println("Validated tags: " + validatedTags.toString());
			System.out.println("Current tag: " + current.toString());
			if (current.isOpenTag()) {
				validatedTags.add(current);
			}
			else if (!current.isOpenTag() && !current.isSelfClosing()) {
				if (!validatedTags.isEmpty() && validatedTags.peek().matches(current)) {
					validatedTags.pop();
				}
				else if (validatedTags.isEmpty()) {
					return null;
				}
				else {
					return validatedTags;
				}
			}
			else {
				//Implied !current.isOpenTag() && current.isSelfClosing() - do nothing
			}
		}
		return validatedTags;
	}
	

}

