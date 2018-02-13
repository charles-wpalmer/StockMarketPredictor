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

package com.aliasi.chunk;

import com.aliasi.corpus.ObjectHandler;

import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

import com.aliasi.util.Strings;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * CUT AND PASTE + TYPE MODS from corpus.ChunkHandlerAdapter
 *
 * @author  Bob Carpenter
 * @version 3.9.1
 * @since   LingPipe3.9.1
 */
class ChunkHandlerAdapter2 
    implements ObjectHandler<Chunking> {

    private final TokenizerFactory mTokenizerFactory;

    private Object mTagHandler;

    private boolean mValidateTokenizer;

    /**
     * Create a chunk handler based on the specified tag handler and
     * tokenizer factory.  The tag handler may be reset later using
     * {@link #setTagHandler(TagHandler)}.  The chunks handled
     * by this handler will be converted to BIO-encoded tag sequences
     *
     * @param tagHandler Tag handler.
     * @param tokenizerFactory Tokenizer factory.
     * @param validateTokenizer Whether or not to validate tokenizer.
     */
    ChunkHandlerAdapter2(Object tagHandler,
                         TokenizerFactory tokenizerFactory,
                         boolean validateTokenizer) {
        this(tokenizerFactory,validateTokenizer);
        mTagHandler = tagHandler;
    }

    /**
     * Construct a chunk handler based on the specified tokenizer
     * factory and an initially null tag handler.  The tag handler may
     * be reset later using {@link #setTagHandler(TagHandler)}.
     *
     * @param tokenizerFactory Tokenizer factory.
     * @param validateTokenizer Whether or not to validate tokenizer.
     */
    public ChunkHandlerAdapter2(TokenizerFactory tokenizerFactory,
                                boolean validateTokenizer) {
        mTokenizerFactory = tokenizerFactory;
        mValidateTokenizer = validateTokenizer;
    }

    /**
     * Set the tag handler to the specified value.
     *
     * @param tagHandler New tag handler for this class.
     * @deprecated See class documentation.
     */
    @Deprecated
    public void setTagHandler(com.aliasi.corpus.TagHandler tagHandler) {
        mTagHandler = tagHandler;
    }

    /**
     * Sets the tokenizer validation status to the specified value.
     * If the value is set to <code>true</code>, then every chunking
     * is tested for whether or not it is consistent with the
     * specified tokenizer for this handler.
     *
     * @param validateTokenizer Whether or not to validate tokenizer.
     */
    public void setValidateTokenizer(boolean validateTokenizer) {
        mValidateTokenizer = validateTokenizer;
    }

    /**
     * Handle the specified chunking by converting it to a tagging
     * using the BIO scheme and contained tokenizer, then delegating
     * to the contained tag handler.
     *
     * @param chunking Chunking to handle.
     * @throws IllegalArgumentException If tokenizer consistency is
     * being validated and the tokenization is not consistent with the
     * specified chunking.
     * @deprecated quiet
     */
    @Deprecated
    public void handle(Chunking chunking) {
        CharSequence cSeq = chunking.charSequence();
        char[] cs = Strings.toCharArray(cSeq);

        Set<Chunk> chunkSet = chunking.chunkSet();
        Chunk[] chunks = chunkSet.<Chunk>toArray(EMPTY_CHUNK_ARRAY);
        Arrays.sort(chunks,Chunk.TEXT_ORDER_COMPARATOR);

        List<String> tokenList = new ArrayList<String>();
        List<String> whiteList = new ArrayList<String>();
        List<String> tagList = new ArrayList<String>();
        int pos = 0;
        for (Chunk nextChunk : chunks) {
            String type = nextChunk.type();
            int start = nextChunk.start();
            int end = nextChunk.end();
            outTag(cs,pos,start,tokenList,whiteList,tagList,mTokenizerFactory);
            chunkTag(cs,start,end,type,tokenList,whiteList,tagList,mTokenizerFactory);
            pos = end;
        }
        outTag(cs,pos,cSeq.length(),tokenList,whiteList,tagList,mTokenizerFactory);
        String[] toks = tokenList.<String>toArray(Strings.EMPTY_STRING_ARRAY);
        String[] whites = whiteList.toArray(Strings.EMPTY_STRING_ARRAY);
        String[] tags = tagList.toArray(Strings.EMPTY_STRING_ARRAY);
        if (mValidateTokenizer
            && !consistentTokens(toks,whites,mTokenizerFactory)) {
            String msg = "Tokens not consistent with tokenizer factory."
                + " Tokens=" + Arrays.asList(toks)
                + " Tokenization=" + tokenization(toks,whites)
                + " Factory class=" + mTokenizerFactory.getClass();
            throw new IllegalArgumentException(msg);
        }
        @SuppressWarnings({"unchecked","deprecation"})
        com.aliasi.corpus.TagHandler handler = (com.aliasi.corpus.TagHandler) mTagHandler; 
        handler.handle(toks,whites,tags);
    }

    /**
     * Returns the array of tags for the specified chunking, relative
     * to the specified tokenizer factory.
     *
     * @param chunking Chunking to convert to tags.
     * @param factory Tokenizer factory for token generation.
     */
    public static String[] toTags(Chunking chunking,
                                  TokenizerFactory factory) {
        CharSequence cSeq = chunking.charSequence();
        char[] cs = Strings.toCharArray(cSeq);

        Set<Chunk> chunkSet = chunking.chunkSet();
        Chunk[] chunks = chunkSet.toArray(EMPTY_CHUNK_ARRAY);
        Arrays.sort(chunks,Chunk.TEXT_ORDER_COMPARATOR);

        List<String> tokenList = new ArrayList<String>();
        List<String> whiteList = new ArrayList<String>();
        List<String> tagList = new ArrayList<String>();
        int pos = 0;
        for (Chunk nextChunk : chunks) {
            String type = nextChunk.type();
            int start = nextChunk.start();
            int end = nextChunk.end();
            outTag(cs,pos,start,tokenList,whiteList,tagList,factory);
            chunkTag(cs,start,end,type,tokenList,whiteList,tagList,factory);
            pos = end;
        }
        outTag(cs,pos,cSeq.length(),tokenList,whiteList,tagList,factory);

        return tagList.toArray(Strings.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns <code>true</code> if the specified tokens and
     * whitespaces are consistent with the specified tokenizer
     * factory.  A tokenizer is consistent with the specified
     * tokens and whitespaces if running the tokenizer over
     * the concatenation of the tokens and whitespaces produces
     * the same tokens and whitespaces.
     *
     * @param toks Tokens to check.
     * @param whitespaces Whitespaces to check.
     * @param tokenizerFactory Factory to create tokenizers.
     * @return <code>true</code> if the tokenizer is consistent with
     * the tokens and whitespaces.
     */
    public static boolean consistentTokens(String[] toks,
                                           String[] whitespaces,
                                           TokenizerFactory tokenizerFactory) {
        if (toks.length+1 != whitespaces.length) return false;
        char[] cs = getChars(toks,whitespaces);
        Tokenizer tokenizer = tokenizerFactory.tokenizer(cs,0,cs.length);
        String nextWhitespace = tokenizer.nextWhitespace();
        if (!whitespaces[0].equals(nextWhitespace)) {
            return false;
        }
        for (int i = 0; i < toks.length; ++i) {
            String token = tokenizer.nextToken();
            if (token == null) {
                return false;
            }
            if (!toks[i].equals(token)) {
                return false;
            }
            nextWhitespace = tokenizer.nextWhitespace();
            if (!whitespaces[i+1].equals(nextWhitespace)) {
                return false;
            }
        }
        return true;
    }

    static void outTag(char[] cs, int start, int end,
                       List<String> tokenList, List<String> whiteList, List<String> tagList,
                       TokenizerFactory factory) {
        Tokenizer tokenizer = factory.tokenizer(cs,start,end-start);
        whiteList.add(tokenizer.nextWhitespace());
        String nextToken;
        while ((nextToken = tokenizer.nextToken()) != null) {
            tokenList.add(nextToken);
            tagList.add(ChunkTagHandlerAdapter2.OUT_TAG);
            whiteList.add(tokenizer.nextWhitespace());
        }

    }

    static void chunkTag(char[] cs, int start, int end, String type,
                         List<String> tokenList, List<String> whiteList, List<String> tagList,
                         TokenizerFactory factory) {
        Tokenizer tokenizer = factory.tokenizer(cs,start,end-start);
        String firstToken = tokenizer.nextToken();
        tokenList.add(firstToken);
        tagList.add(ChunkTagHandlerAdapter2.BEGIN_TAG_PREFIX + type);
        while (true) {
            String nextWhitespace = tokenizer.nextWhitespace();
            String nextToken = tokenizer.nextToken();
            if (nextToken == null) break;
            tokenList.add(nextToken);
            whiteList.add(nextWhitespace);
            tagList.add(ChunkTagHandlerAdapter2.IN_TAG_PREFIX + type);
        }
    }


    List<String> tokenization(String[] toks, String[] whitespaces) {
        List<String> tokList = new ArrayList<String>();
        List<String> whiteList = new ArrayList<String>();
        char[] cs = getChars(toks,whitespaces);
        Tokenizer tokenizer = mTokenizerFactory.tokenizer(cs,0,cs.length);
        tokenizer.tokenize(tokList,whiteList);
        return tokList;
    }

    static char[] getChars(String[] toks, String[] whitespaces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < toks.length; ++i) {
            sb.append(whitespaces[i]);
            sb.append(toks[i]);
        }
        sb.append(whitespaces[whitespaces.length-1]);
        return Strings.toCharArray(sb);
    }

    static final Chunk[] EMPTY_CHUNK_ARRAY = new Chunk[0];

}
