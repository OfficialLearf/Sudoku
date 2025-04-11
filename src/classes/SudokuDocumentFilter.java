package classes;

import javax.swing.text.*;

/**
 * The type Sudoku document filter.
 */
class SudokuDocumentFilter extends DocumentFilter {
    private final int maxNumber;


    /**
     * Instantiates a new Sudoku document filter.
     *
     * @param maxNumber the max number
     */
    public SudokuDocumentFilter(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isValidInput(string, fb.getDocument())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isValidInput(text, fb.getDocument())) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isValidInput(String text, Document document) {
        int maxAllowedDigits = (maxNumber == 16) ? 2 : 1;
        if (!text.isEmpty() && text.length() <= maxAllowedDigits) {
            try {
                int number = Integer.parseInt(text);
                if (number < 1 || number > maxNumber) {
                    return false;
                }
                return document.getLength() + text.length() <= maxAllowedDigits;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (text.isEmpty()) {

            return true;
        }
        return false;
    }
}
