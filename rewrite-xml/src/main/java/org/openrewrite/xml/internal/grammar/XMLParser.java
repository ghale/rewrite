/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.xml.internal.grammar;

// Generated from /Users/yoshi/Development/Repos/openrewrite/rewrite/rewrite-xml/src/main/antlr/XMLParser.g4 by ANTLR 4.9.2



import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XMLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	public static final int
			COMMENT=1, CDATA=2, ParamEntityRef=3, EntityRef=4, CharRef=5, SEA_WS=6,
			SPECIAL_OPEN_XML=7, OPEN=8, SPECIAL_OPEN=9, DTD_OPEN=10, TEXT=11, DTD_CLOSE=12,
			DTD_SUBSET_OPEN=13, DTD_S=14, DOCTYPE=15, DTD_SUBSET_CLOSE=16, MARKUP_OPEN=17,
			DTS_SUBSET_S=18, MARK_UP_CLOSE=19, MARKUP_S=20, MARKUP_TEXT=21, MARKUP_SUBSET=22,
			PI_S=23, PI_TEXT=24, CLOSE=25, SPECIAL_CLOSE=26, SLASH_CLOSE=27, S=28,
			SLASH=29, EQUALS=30, STRING=31, Name=32;
	public static final int
			RULE_document = 0, RULE_prolog = 1, RULE_xmldecl = 2, RULE_misc = 3, RULE_doctypedecl = 4,
			RULE_intsubset = 5, RULE_markupdecl = 6, RULE_declSep = 7, RULE_externalid = 8,
			RULE_processinginstruction = 9, RULE_content = 10, RULE_element = 11,
			RULE_reference = 12, RULE_attribute = 13, RULE_chardata = 14;
	private static String[] makeRuleNames() {
		return new String[] {
				"document", "prolog", "xmldecl", "misc", "doctypedecl", "intsubset",
				"markupdecl", "declSep", "externalid", "processinginstruction", "content",
				"element", "reference", "attribute", "chardata"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
				null, null, null, null, null, null, null, null, "'<'", null, null, null,
				null, null, null, "'DOCTYPE'", null, null, null, null, null, null, null,
				null, null, null, "'?>'", "'/>'", null, "'/'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
				null, "COMMENT", "CDATA", "ParamEntityRef", "EntityRef", "CharRef", "SEA_WS",
				"SPECIAL_OPEN_XML", "OPEN", "SPECIAL_OPEN", "DTD_OPEN", "TEXT", "DTD_CLOSE",
				"DTD_SUBSET_OPEN", "DTD_S", "DOCTYPE", "DTD_SUBSET_CLOSE", "MARKUP_OPEN",
				"DTS_SUBSET_S", "MARK_UP_CLOSE", "MARKUP_S", "MARKUP_TEXT", "MARKUP_SUBSET",
				"PI_S", "PI_TEXT", "CLOSE", "SPECIAL_CLOSE", "SLASH_CLOSE", "S", "SLASH",
				"EQUALS", "STRING", "Name"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "XMLParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XMLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class DocumentContext extends ParserRuleContext {
		public PrologContext prolog() {
			return getRuleContext(PrologContext.class,0);
		}
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitDocument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitDocument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_document);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(30);
				prolog();
				setState(31);
				element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrologContext extends ParserRuleContext {
		public XmldeclContext xmldecl() {
			return getRuleContext(XmldeclContext.class,0);
		}
		public List<MiscContext> misc() {
			return getRuleContexts(MiscContext.class);
		}
		public MiscContext misc(int i) {
			return getRuleContext(MiscContext.class,i);
		}
		public PrologContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prolog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterProlog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitProlog(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitProlog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrologContext prolog() throws RecognitionException {
		PrologContext _localctx = new PrologContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prolog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPECIAL_OPEN_XML) {
					{
						setState(33);
						xmldecl();
					}
				}

				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << SPECIAL_OPEN) | (1L << DTD_OPEN))) != 0)) {
					{
						{
							setState(36);
							misc();
						}
					}
					setState(41);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XmldeclContext extends ParserRuleContext {
		public TerminalNode SPECIAL_OPEN_XML() { return getToken(XMLParser.SPECIAL_OPEN_XML, 0); }
		public TerminalNode SPECIAL_CLOSE() { return getToken(XMLParser.SPECIAL_CLOSE, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public XmldeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xmldecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterXmldecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitXmldecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitXmldecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XmldeclContext xmldecl() throws RecognitionException {
		XmldeclContext _localctx = new XmldeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_xmldecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(42);
				match(SPECIAL_OPEN_XML);
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
						{
							setState(43);
							attribute();
						}
					}
					setState(48);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(49);
				match(SPECIAL_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MiscContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(XMLParser.COMMENT, 0); }
		public DoctypedeclContext doctypedecl() {
			return getRuleContext(DoctypedeclContext.class,0);
		}
		public ProcessinginstructionContext processinginstruction() {
			return getRuleContext(ProcessinginstructionContext.class,0);
		}
		public MiscContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_misc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterMisc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitMisc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitMisc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MiscContext misc() throws RecognitionException {
		MiscContext _localctx = new MiscContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_misc);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(54);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case COMMENT:
					{
						setState(51);
						match(COMMENT);
					}
					break;
					case DTD_OPEN:
					{
						setState(52);
						doctypedecl();
					}
					break;
					case SPECIAL_OPEN:
					{
						setState(53);
						processinginstruction();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoctypedeclContext extends ParserRuleContext {
		public TerminalNode DTD_OPEN() { return getToken(XMLParser.DTD_OPEN, 0); }
		public TerminalNode DOCTYPE() { return getToken(XMLParser.DOCTYPE, 0); }
		public TerminalNode Name() { return getToken(XMLParser.Name, 0); }
		public ExternalidContext externalid() {
			return getRuleContext(ExternalidContext.class,0);
		}
		public TerminalNode DTD_CLOSE() { return getToken(XMLParser.DTD_CLOSE, 0); }
		public List<TerminalNode> STRING() { return getTokens(XMLParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(XMLParser.STRING, i);
		}
		public TerminalNode DTD_SUBSET_OPEN() { return getToken(XMLParser.DTD_SUBSET_OPEN, 0); }
		public IntsubsetContext intsubset() {
			return getRuleContext(IntsubsetContext.class,0);
		}
		public TerminalNode DTD_SUBSET_CLOSE() { return getToken(XMLParser.DTD_SUBSET_CLOSE, 0); }
		public DoctypedeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doctypedecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterDoctypedecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitDoctypedecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitDoctypedecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoctypedeclContext doctypedecl() throws RecognitionException {
		DoctypedeclContext _localctx = new DoctypedeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_doctypedecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(56);
				match(DTD_OPEN);
				setState(57);
				match(DOCTYPE);
				setState(58);
				match(Name);
				setState(59);
				externalid();
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==STRING) {
					{
						{
							setState(60);
							match(STRING);
						}
					}
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DTD_SUBSET_OPEN) {
					{
						setState(66);
						match(DTD_SUBSET_OPEN);
						setState(67);
						intsubset();
						setState(68);
						match(DTD_SUBSET_CLOSE);
					}
				}

				setState(72);
				match(DTD_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntsubsetContext extends ParserRuleContext {
		public List<MarkupdeclContext> markupdecl() {
			return getRuleContexts(MarkupdeclContext.class);
		}
		public MarkupdeclContext markupdecl(int i) {
			return getRuleContext(MarkupdeclContext.class,i);
		}
		public List<DeclSepContext> declSep() {
			return getRuleContexts(DeclSepContext.class);
		}
		public DeclSepContext declSep(int i) {
			return getRuleContext(DeclSepContext.class,i);
		}
		public IntsubsetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intsubset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterIntsubset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitIntsubset(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitIntsubset(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntsubsetContext intsubset() throws RecognitionException {
		IntsubsetContext _localctx = new IntsubsetContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_intsubset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << ParamEntityRef) | (1L << SPECIAL_OPEN) | (1L << MARKUP_OPEN))) != 0)) {
					{
						setState(76);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
							case COMMENT:
							case SPECIAL_OPEN:
							case MARKUP_OPEN:
							{
								setState(74);
								markupdecl();
							}
							break;
							case ParamEntityRef:
							{
								setState(75);
								declSep();
							}
							break;
							default:
								throw new NoViableAltException(this);
						}
					}
					setState(80);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MarkupdeclContext extends ParserRuleContext {
		public TerminalNode MARKUP_OPEN() { return getToken(XMLParser.MARKUP_OPEN, 0); }
		public TerminalNode MARK_UP_CLOSE() { return getToken(XMLParser.MARK_UP_CLOSE, 0); }
		public List<TerminalNode> MARKUP_TEXT() { return getTokens(XMLParser.MARKUP_TEXT); }
		public TerminalNode MARKUP_TEXT(int i) {
			return getToken(XMLParser.MARKUP_TEXT, i);
		}
		public List<TerminalNode> MARKUP_SUBSET() { return getTokens(XMLParser.MARKUP_SUBSET); }
		public TerminalNode MARKUP_SUBSET(int i) {
			return getToken(XMLParser.MARKUP_SUBSET, i);
		}
		public ProcessinginstructionContext processinginstruction() {
			return getRuleContext(ProcessinginstructionContext.class,0);
		}
		public TerminalNode COMMENT() { return getToken(XMLParser.COMMENT, 0); }
		public MarkupdeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_markupdecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterMarkupdecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitMarkupdecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitMarkupdecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MarkupdeclContext markupdecl() throws RecognitionException {
		MarkupdeclContext _localctx = new MarkupdeclContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_markupdecl);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case MARKUP_OPEN:
					enterOuterAlt(_localctx, 1);
				{
					{
						setState(81);
						match(MARKUP_OPEN);
						setState(83);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
							case 1:
							{
								setState(82);
								match(MARKUP_TEXT);
							}
							break;
						}
						setState(88);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==MARKUP_SUBSET) {
							{
								{
									setState(85);
									match(MARKUP_SUBSET);
								}
							}
							setState(90);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(92);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==MARKUP_TEXT) {
							{
								setState(91);
								match(MARKUP_TEXT);
							}
						}

						setState(94);
						match(MARK_UP_CLOSE);
					}
				}
				break;
				case SPECIAL_OPEN:
					enterOuterAlt(_localctx, 2);
				{
					setState(95);
					processinginstruction();
				}
				break;
				case COMMENT:
					enterOuterAlt(_localctx, 3);
				{
					setState(96);
					match(COMMENT);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclSepContext extends ParserRuleContext {
		public TerminalNode ParamEntityRef() { return getToken(XMLParser.ParamEntityRef, 0); }
		public DeclSepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declSep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterDeclSep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitDeclSep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitDeclSep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclSepContext declSep() throws RecognitionException {
		DeclSepContext _localctx = new DeclSepContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declSep);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(99);
				match(ParamEntityRef);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExternalidContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(XMLParser.Name, 0); }
		public ExternalidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_externalid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterExternalid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitExternalid(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitExternalid(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExternalidContext externalid() throws RecognitionException {
		ExternalidContext _localctx = new ExternalidContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_externalid);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Name) {
					{
						setState(101);
						match(Name);
					}
				}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcessinginstructionContext extends ParserRuleContext {
		public TerminalNode SPECIAL_OPEN() { return getToken(XMLParser.SPECIAL_OPEN, 0); }
		public TerminalNode PI_TEXT() { return getToken(XMLParser.PI_TEXT, 0); }
		public TerminalNode SPECIAL_CLOSE() { return getToken(XMLParser.SPECIAL_CLOSE, 0); }
		public ProcessinginstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_processinginstruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterProcessinginstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitProcessinginstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitProcessinginstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessinginstructionContext processinginstruction() throws RecognitionException {
		ProcessinginstructionContext _localctx = new ProcessinginstructionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_processinginstruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(104);
				match(SPECIAL_OPEN);
				setState(105);
				match(PI_TEXT);
				setState(106);
				match(SPECIAL_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContentContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public ProcessinginstructionContext processinginstruction() {
			return getRuleContext(ProcessinginstructionContext.class,0);
		}
		public TerminalNode CDATA() { return getToken(XMLParser.CDATA, 0); }
		public TerminalNode COMMENT() { return getToken(XMLParser.COMMENT, 0); }
		public ChardataContext chardata() {
			return getRuleContext(ChardataContext.class,0);
		}
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_content);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(114);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case OPEN:
					{
						setState(108);
						element();
					}
					break;
					case EntityRef:
					case CharRef:
					{
						setState(109);
						reference();
					}
					break;
					case SPECIAL_OPEN:
					{
						setState(110);
						processinginstruction();
					}
					break;
					case CDATA:
					{
						setState(111);
						match(CDATA);
					}
					break;
					case COMMENT:
					{
						setState(112);
						match(COMMENT);
					}
					break;
					case SEA_WS:
					case TEXT:
					{
						setState(113);
						chardata();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public List<TerminalNode> OPEN() { return getTokens(XMLParser.OPEN); }
		public TerminalNode OPEN(int i) {
			return getToken(XMLParser.OPEN, i);
		}
		public List<TerminalNode> Name() { return getTokens(XMLParser.Name); }
		public TerminalNode Name(int i) {
			return getToken(XMLParser.Name, i);
		}
		public List<TerminalNode> CLOSE() { return getTokens(XMLParser.CLOSE); }
		public TerminalNode CLOSE(int i) {
			return getToken(XMLParser.CLOSE, i);
		}
		public TerminalNode SLASH() { return getToken(XMLParser.SLASH, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public TerminalNode SLASH_CLOSE() { return getToken(XMLParser.SLASH_CLOSE, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_element);
		int _la;
		try {
			int _alt;
			setState(144);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(116);
					match(OPEN);
					setState(117);
					match(Name);
					setState(121);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Name) {
						{
							{
								setState(118);
								attribute();
							}
						}
						setState(123);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(124);
					match(CLOSE);
					setState(128);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
								{
									setState(125);
									content();
								}
							}
						}
						setState(130);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
					}
					setState(131);
					match(OPEN);
					setState(132);
					match(SLASH);
					setState(133);
					match(Name);
					setState(134);
					match(CLOSE);
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(135);
					match(OPEN);
					setState(136);
					match(Name);
					setState(140);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Name) {
						{
							{
								setState(137);
								attribute();
							}
						}
						setState(142);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(143);
					match(SLASH_CLOSE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReferenceContext extends ParserRuleContext {
		public TerminalNode EntityRef() { return getToken(XMLParser.EntityRef, 0); }
		public TerminalNode CharRef() { return getToken(XMLParser.CharRef, 0); }
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_reference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(146);
				_la = _input.LA(1);
				if ( !(_la==EntityRef || _la==CharRef) ) {
					_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(XMLParser.Name, 0); }
		public TerminalNode EQUALS() { return getToken(XMLParser.EQUALS, 0); }
		public TerminalNode STRING() { return getToken(XMLParser.STRING, 0); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(148);
				match(Name);
				setState(149);
				match(EQUALS);
				setState(150);
				match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ChardataContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(XMLParser.TEXT, 0); }
		public TerminalNode SEA_WS() { return getToken(XMLParser.SEA_WS, 0); }
		public ChardataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chardata; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).enterChardata(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XMLParserListener ) ((XMLParserListener)listener).exitChardata(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XMLParserVisitor ) return ((XMLParserVisitor<? extends T>)visitor).visitChardata(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChardataContext chardata() throws RecognitionException {
		ChardataContext _localctx = new ChardataContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_chardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(152);
				_la = _input.LA(1);
				if ( !(_la==SEA_WS || _la==TEXT) ) {
					_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\"\u009d\4\2\t\2\4"+
					"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
					"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\3\5\3"+
					"%\n\3\3\3\7\3(\n\3\f\3\16\3+\13\3\3\4\3\4\7\4/\n\4\f\4\16\4\62\13\4\3"+
					"\4\3\4\3\5\3\5\3\5\5\59\n\5\3\6\3\6\3\6\3\6\3\6\7\6@\n\6\f\6\16\6C\13"+
					"\6\3\6\3\6\3\6\3\6\5\6I\n\6\3\6\3\6\3\7\3\7\7\7O\n\7\f\7\16\7R\13\7\3"+
					"\b\3\b\5\bV\n\b\3\b\7\bY\n\b\f\b\16\b\\\13\b\3\b\5\b_\n\b\3\b\3\b\3\b"+
					"\5\bd\n\b\3\t\3\t\3\n\5\ni\n\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
					"\3\f\5\fu\n\f\3\r\3\r\3\r\7\rz\n\r\f\r\16\r}\13\r\3\r\3\r\7\r\u0081\n"+
					"\r\f\r\16\r\u0084\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u008d\n\r\f\r\16"+
					"\r\u0090\13\r\3\r\5\r\u0093\n\r\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20"+
					"\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\4\3\2\6\7\4\2\b"+
					"\b\r\r\2\u00a5\2 \3\2\2\2\4$\3\2\2\2\6,\3\2\2\2\b8\3\2\2\2\n:\3\2\2\2"+
					"\fP\3\2\2\2\16c\3\2\2\2\20e\3\2\2\2\22h\3\2\2\2\24j\3\2\2\2\26t\3\2\2"+
					"\2\30\u0092\3\2\2\2\32\u0094\3\2\2\2\34\u0096\3\2\2\2\36\u009a\3\2\2\2"+
					" !\5\4\3\2!\"\5\30\r\2\"\3\3\2\2\2#%\5\6\4\2$#\3\2\2\2$%\3\2\2\2%)\3\2"+
					"\2\2&(\5\b\5\2\'&\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\5\3\2\2\2+)"+
					"\3\2\2\2,\60\7\t\2\2-/\5\34\17\2.-\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60"+
					"\61\3\2\2\2\61\63\3\2\2\2\62\60\3\2\2\2\63\64\7\34\2\2\64\7\3\2\2\2\65"+
					"9\7\3\2\2\669\5\n\6\2\679\5\24\13\28\65\3\2\2\28\66\3\2\2\28\67\3\2\2"+
					"\29\t\3\2\2\2:;\7\f\2\2;<\7\21\2\2<=\7\"\2\2=A\5\22\n\2>@\7!\2\2?>\3\2"+
					"\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BH\3\2\2\2CA\3\2\2\2DE\7\17\2\2EF\5"+
					"\f\7\2FG\7\22\2\2GI\3\2\2\2HD\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JK\7\16\2\2K"+
					"\13\3\2\2\2LO\5\16\b\2MO\5\20\t\2NL\3\2\2\2NM\3\2\2\2OR\3\2\2\2PN\3\2"+
					"\2\2PQ\3\2\2\2Q\r\3\2\2\2RP\3\2\2\2SU\7\23\2\2TV\7\27\2\2UT\3\2\2\2UV"+
					"\3\2\2\2VZ\3\2\2\2WY\7\30\2\2XW\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2"+
					"[^\3\2\2\2\\Z\3\2\2\2]_\7\27\2\2^]\3\2\2\2^_\3\2\2\2_`\3\2\2\2`d\7\25"+
					"\2\2ad\5\24\13\2bd\7\3\2\2cS\3\2\2\2ca\3\2\2\2cb\3\2\2\2d\17\3\2\2\2e"+
					"f\7\5\2\2f\21\3\2\2\2gi\7\"\2\2hg\3\2\2\2hi\3\2\2\2i\23\3\2\2\2jk\7\13"+
					"\2\2kl\7\32\2\2lm\7\34\2\2m\25\3\2\2\2nu\5\30\r\2ou\5\32\16\2pu\5\24\13"+
					"\2qu\7\4\2\2ru\7\3\2\2su\5\36\20\2tn\3\2\2\2to\3\2\2\2tp\3\2\2\2tq\3\2"+
					"\2\2tr\3\2\2\2ts\3\2\2\2u\27\3\2\2\2vw\7\n\2\2w{\7\"\2\2xz\5\34\17\2y"+
					"x\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2}{\3\2\2\2~\u0082\7\33"+
					"\2\2\177\u0081\5\26\f\2\u0080\177\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080"+
					"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085"+
					"\u0086\7\n\2\2\u0086\u0087\7\37\2\2\u0087\u0088\7\"\2\2\u0088\u0093\7"+
					"\33\2\2\u0089\u008a\7\n\2\2\u008a\u008e\7\"\2\2\u008b\u008d\5\34\17\2"+
					"\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f"+
					"\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0093\7\35\2\2"+
					"\u0092v\3\2\2\2\u0092\u0089\3\2\2\2\u0093\31\3\2\2\2\u0094\u0095\t\2\2"+
					"\2\u0095\33\3\2\2\2\u0096\u0097\7\"\2\2\u0097\u0098\7 \2\2\u0098\u0099"+
					"\7!\2\2\u0099\35\3\2\2\2\u009a\u009b\t\3\2\2\u009b\37\3\2\2\2\24$)\60"+
					"8AHNPUZ^cht{\u0082\u008e\u0092";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
