package accessprotectedmethod;

import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
import javax.swing.text.StringContent;

// BEGIN: public.accessor
public class MyDocument  extends AbstractDocument {
    public MyDocument() {super(new StringContent());}
    
    final void writeLockAccess() {
        writeLock();
    }
// FINISH: public.accessor

    @Override
    public Element getDefaultRootElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Element getParagraphElement(int pos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
