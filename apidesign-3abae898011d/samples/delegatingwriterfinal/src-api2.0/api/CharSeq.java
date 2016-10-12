package api;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
final class CharSeq implements CharSequence {
    final char[] arr;
    final int off;
    final int len;
    final int c;
    
    public CharSeq(int c) {
        this.arr = null;
        this.off = 0;
        this.len = 1;
        this.c = c;
    }

    public CharSeq(char[] arr, int off, int len) {
        this.arr = arr;
        this.off = off;
        this.len = len;
        this.c = -1;
    }

    public int length() {
        return arr == null ? 1 : len;
    }

    public char charAt(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (arr == null) {
            if (index > 0) {
                throw new IndexOutOfBoundsException();
            }
            return (char)c;
        } else {
            if (index >= len) {
                throw new IndexOutOfBoundsException();
            }
            return arr[off + index];
        }
    }

    public CharSequence subSequence(int start, int end) {
        if (end >= this.len) {
            throw new IndexOutOfBoundsException();
        }
        char[] array = arr == null ? new char[]{ (char)c } : arr;
        return new CharSeq(array, off + start, off + end);
    }

}
