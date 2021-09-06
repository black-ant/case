package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Classname Num_211_AddAndSearchWordDataStructureDesign
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_211_AddAndSearchWordDataStructureDesign extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_211_AddAndSearchWordDataStructureDesign().run();
    }

    @Override
    public void run() {

    }


    class WordDictionary {
        HashMap<Integer, HashSet<String>> map;

        /**
         * Initialize your data structure here.
         */
        public WordDictionary() {
            map = new HashMap<>();
        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            int n = word.length();
            //将字符串加入对应长度的 set 中
            if (map.containsKey(n)) {
                HashSet<String> set = map.get(n);
                set.add(word);
            } else {
                HashSet<String> set = new HashSet<String>();
                set.add(word);
                map.put(n, set);
            }
        }

        /**
         * Returns if the word is in the data structure. A word could contain the
         * dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            HashSet<String> set = map.getOrDefault(word.length(), new HashSet<String>());
            if (set.contains(word)) {
                return true;
            }
            for (String s : set) {
                if (equal(s, word)) {
                    return true;
                }
            }
            return false;
        }

        private boolean equal(String s, String word) {
            char[] c1 = s.toCharArray();
            char[] c2 = word.toCharArray();
            int n1 = s.length();
            int n2 = word.length();
            if (n1 != n2) {
                return false;
            }
            for (int i = 0; i < n1; i++) {
                if (c1[i] != c2[i] && c2[i] != '.') {
                    return false;
                }
            }
            return true;
        }
    }
}
