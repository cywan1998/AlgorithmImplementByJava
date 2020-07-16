package LeetCode;

import java.util.*;

/**
 * 单词转换路径问题
 * 思路： 思路和程序员代码面试指南有所不同。书上采用BFS+DFS的方法得到路径，首先通过BFS得到每个节点到起点的最短距离，然后沿着最短距离进行DFS
 * 我通过哈希表保存BFS的遍历过程，key:当前节点，value:当前节点的前驱。由此可以通过endWord反向遍历至startWord，而且是最短的路。时间空间复杂度
 * 均更短。
 */
public class WordChangePath {
    // 构造后继图，每个单词的后继列表
    public void buildNextWordsMap(HashMap<String, List<String>> nextWordsMap, Set<String> wordSet) {
        char[] wordArr;
        for (var word : wordSet) {
            wordArr = word.toCharArray();
            List<String> nextWords = new ArrayList<>();
            //对于每个单词，每次变换一位，然后查看worSet中是否含有，有的话就是后继。
            for (int i = 0; i < wordArr.length; i++) {
                for (char j = 'a'; j <= 'z'; j++) {
                    if (wordArr[i] != j) {
                        char temp = wordArr[i];
                        wordArr[i] = j;
                        String tempWord = String.valueOf(wordArr);
                        if (wordSet.contains(tempWord)) {
                            nextWords.add(tempWord);
                        }
                        //还原
                        wordArr[i] = temp;
                    }
                }
            }
            nextWordsMap.put(word, nextWords);
        }
    }

    //执行广度优先搜索
    public List<String> BFS(HashMap<String, List<String>> nextWordsMap, String beginWord, String endWord) {
        //保存结果
        List<String> changePaths = new LinkedList<>();
        //保存搜索路径，key表示当前节点，value是当前节点的前驱节点，最后从endWord反向索引至beginWord即得到最短的变换路径
        HashMap<String, String> findPathMap = new HashMap<>();

        Queue<String> queue = new LinkedList<>();
        Set<String> isVisited = new HashSet<>();
        queue.offer(beginWord);
        isVisited.add(beginWord);
        outer:
        while (!queue.isEmpty()) {
            String word = queue.poll();
            //遍历后继
            for (var nextWord : nextWordsMap.get(word)) {
                if (!isVisited.contains(nextWord)) {
                    queue.add(nextWord);
                    findPathMap.put(nextWord, word);
                    isVisited.add(nextWord);
                    if (nextWord.equals(endWord)) {
                        break outer;
                    }
                }
            }
        }
        //反向遍历得到索引路径，因为每个节点只有一个前驱，如果起点到终点有路径就可以到达。
        String cur = endWord;
        changePaths.add(cur);
        String pre;
        do {
            pre = findPathMap.get(cur);
            if (pre == null) {
                return new ArrayList<>();
            }
            changePaths.add(pre);
            cur = pre;
        } while (!cur.equals(beginWord));
        //反转
        Collections.reverse(changePaths);
        return changePaths;
    }

    public List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        // 保存单词集
        Set<String> wordSet = new HashSet<>();
        wordSet.add(beginWord);
        wordSet.addAll(wordList);
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }
        // 保存后继图
        HashMap<String, List<String>> nextWordsMap = new HashMap<>();
        buildNextWordsMap(nextWordsMap, wordSet);
        //执行广度优先遍历
        return BFS(nextWordsMap, beginWord, endWord);
    }

    public static void main(String[] args) {
        List<String> wordList = new ArrayList<>(Arrays.asList("hot", "dog"));
        WordChangePath so = new WordChangePath();
        System.out.println(so.findLadders("hot", "dog", wordList));
    }
}
