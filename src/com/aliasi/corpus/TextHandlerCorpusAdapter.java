/*
 * LingPipe v. 3.9
 * Copyright (C) 2003-2010 Alias-i
 *
 * This program is licensed under the Alias-i Royalty Free License
 * Version 1 WITHOUT ANY WARRANTY, without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Alias-i
 * Royalty Free License Version 1 for more details.
 *
 * You should have received a copy of the Alias-i Royalty Free License
 * Version 1 along with this program; if not, visit
 * http://alias-i.com/lingpipe/licenses/lingpipe-license-1.txt or contact
 * Alias-i, Inc. at 181 North 11th Street, Suite 401, Brooklyn, NY 11211,
 * +1 (718) 290-9170.
 */

package com.aliasi.corpus;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;

import com.aliasi.corpus.Corpus;
import com.aliasi.corpus.ObjectHandler;

import java.io.IOException;

/**
 * @deprecated Do not use.
 */
@SuppressWarnings("deprecation") @Deprecated
class TextHandlerCorpusAdapter
    extends Corpus<ObjectHandler<CharSequence>> {
    
    /**
     * @deprecated Silencing.
     */
    @SuppressWarnings("deprecation") @Deprecated
    private final Corpus<com.aliasi.corpus.TextHandler> mCorpus;

    /**
     * Construct a corpus adapter for the specified corpus.
     *
     * @param corpus Corpus to adapt.
     * @deprecated Silencing.
     */
    @SuppressWarnings("deprecation") @Deprecated
    public TextHandlerCorpusAdapter(Corpus<com.aliasi.corpus.TextHandler> corpus) {
        mCorpus = corpus;
    }

    /**
     * @inheritDoc
     */
    public void visitTrain(ObjectHandler<CharSequence> handler) throws IOException {
        mCorpus.visitTrain(new HandlerAdapter(handler));
    }

    /**
     * @inheritDoc
     */
    public void visitTest(ObjectHandler<CharSequence> handler) throws IOException {
        mCorpus.visitTest(new HandlerAdapter(handler));
    }


    /**
     * @deprecated Silencing.
     */
    @SuppressWarnings("deprecation")    @Deprecated
    static class HandlerAdapter implements com.aliasi.corpus.TextHandler {
        private final ObjectHandler<CharSequence> mHandler;
        HandlerAdapter(ObjectHandler<CharSequence> handler) {
            mHandler = handler;
        }
        public void handle(char[] cs, int start, int len) {
            mHandler.handle(new String(cs,start,len));
        }
    }



}

