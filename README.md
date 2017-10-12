# Bigram_Probabilities

An automatic speech recognition system has provided a written sentence as the
possible interpretation to a speech input.

Compute the probability of a written sentence using the bigram language model
trained on HW2_F17_NLP6320-NLPCorpusTreebank2Parts-CorpusA.txt.

Note: Please use whitespace (i.e. space, tab, and newline) to tokenize the corpus
into words/tokens that are required for the bigram model. Do NOT perform any
type of word/token normalization (i.e. stem, lemmatize, lowercase, etc.). Creation
and matching of bigrams should be exact and case-sensitive. Do NOT split the
corpus into sentences. Please consider the entire corpus as a single string for
tokenization and computation of bigrams.

Compute the sentence probability under the three following scenarios:
i. Use the bigram model without smoothing.
ii. Use the bigram model with add-one smoothing
iii. Use the bigram model with Good-Turing discounting.

Your computer program should do the following:
1. Compute the bigram counts on the given corpus (HW2_F17_NLP6320-
NLPCorpusTreebank2Parts-CorpusA.txt).
2. For a given input written sentence:
a. For each of the three scenarios, construct a table with the bigram counts
for the sentence.
b. For each of the three scenarios, construct a table with the bigram
probabilities for the sentence.
c. For each of the three scenarios, compute the total probability for the
sentence.
