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

package com.aliasi.features;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;

import com.aliasi.corpus.Corpus;
import com.aliasi.corpus.ObjectHandler;

import java.io.IOException;

/**
 * CUT AND PASTE FROM CORPUS
 * @deprecated silence
 */
@Deprecated
class ClassificationHandlerCorpusAdapter3<E> 
    extends Corpus<ObjectHandler<Classified<E>>> {

    /**
     * @deprecated Silencing.
     */
    @SuppressWarnings("deprecation") @Deprecated
    private final Corpus<com.aliasi.corpus.ClassificationHandler<E,Classification>> mCorpus;

    /**
     * Construct a corpus adapter for the specified corpus.
     *
     * @param corpus Corpus to adapt.
     * @deprecated Silencing.
     */
    @SuppressWarnings("deprecation") @Deprecated
    public ClassificationHandlerCorpusAdapter3(Corpus<com.aliasi.corpus.ClassificationHandler<E,Classification>> corpus) {
        mCorpus = corpus;
    }

    /**
     * @inheritDoc
     */
    public void visitTrain(ObjectHandler<Classified<E>> handler) throws IOException {
        mCorpus.visitTrain(new HandlerAdapter<E>(handler));
    }

    /**
     * @inheritDoc
     */
    public void visitTest(ObjectHandler<Classified<E>> handler) throws IOException {
        mCorpus.visitTest(new HandlerAdapter<E>(handler));
    }


    /**
     * @deprecated Silencing.
     */
    @SuppressWarnings("deprecation")    @Deprecated
    static class HandlerAdapter<F> implements com.aliasi.corpus.ClassificationHandler<F,Classification> {
        private final ObjectHandler<Classified<F>> mHandler;
        HandlerAdapter(ObjectHandler<Classified<F>> handler) {
            mHandler = handler;
        }
        public void handle(F cs, Classification c) {
            mHandler.handle(new Classified<F>(cs,c));
        }
    }



}
