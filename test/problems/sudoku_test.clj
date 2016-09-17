(ns problems.sudoku-test
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [problems.sudoku :as su]
            [clojure.test :as t :refer[is testing deftest]]))

(def test-chunk [ 0  1  2  3  4  5  6  7  8
                 9 10 11 12 13 14 15 16 17
                 18 19 20 21 22 23 24 25 26])

(def good-state [5 3 0 0 7 0 0 0 0
                 6 0 0 1 9 5 0 0 0
                 0 9 8 0 0 0 0 6 0
                 8 0 0 0 6 0 0 0 3
                 4 0 0 8 0 3 0 0 1
                 7 0 0 0 2 0 0 0 6
                 0 6 0 0 0 0 2 8 0
                 0 0 0 4 1 9 0 0 5
                 0 0 0 0 8 0 0 7 9])
(def bad-block-state [5 3 0 0 7 0 0 0 0
                      6 0 0 1 9 5 0 0 0
                      0 9 8 0 0 0 0 6 0
                      8 0 0 0 6 0 0 0 3
                      4 0 0 8 0 3 0 0 1
                      7 0 0 0 2 8 0 0 6
                      0 6 0 0 0 0 2 8 0
                      0 0 0 4 1 9 0 0 5
                      0 0 0 0 8 0 0 7 9])
(def bad-vertical-state [5 3 0 0 7 0 0 0 0
                      6 0 0 1 9 5 0 0 0
                      0 9 8 0 0 0 0 6 0
                      8 0 0 0 6 0 0 0 3
                      4 0 0 8 0 3 0 0 1
                      7 0 0 0 2 0 0 0 6
                      0 6 0 0 0 0 2 8 0
                      0 0 0 4 1 9 0 0 5
                      5 0 0 0 8 0 0 7 9])
(def bad-horizontal-state [5 3 0 0 7 0 0 0 5
                         6 0 0 1 9 5 0 0 0
                         0 9 8 0 0 0 0 6 0
                         8 0 0 0 6 0 0 0 3
                         4 0 0 8 0 3 0 0 1
                         7 0 0 0 2 0 0 0 6
                         0 6 0 0 0 0 2 8 0
                         0 0 0 4 1 9 0 0 5
                         0 0 0 0 8 0 0 7 9])

(deftest test-chunking
  (testing "that chunks are handled correctly"
    (is (= (su/get-blocks test-chunk)
           '([0 9 18 1 10 19 2 11 20]
             [3 12 21 4 13 22 5 14 23]
             [6 15 24 7 16 25 8 17 26])))))

(deftest test-validation
  (testing "that blocks are checked correctly"
    (is (su/blocks-valid? good-state))
    (is (not (su/blocks-valid? bad-block-state))))
  (testing "that columns are checked correctly"
    (is (su/vertical-valid? good-state))
    (is (not (su/vertical-valid? bad-vertical-state))))
  (testing "that rows are checked correctly"
    (is (su/horizontal-valid? good-state))
    (is (not (su/horizontal-valid? bad-horizontal-state)))))

(deftest test-children
  (testing "that the correct children are made"
    (is (= (su/children good-state)
          '([5 3 1 0 7 0 0 0 0
           6 0 0 1 9 5 0 0 0
           0 9 8 0 0 0 0 6 0
           8 0 0 0 6 0 0 0 3
           4 0 0 8 0 3 0 0 1
           7 0 0 0 2 0 0 0 6
           0 6 0 0 0 0 2 8 0
           0 0 0 4 1 9 0 0 5
           0 0 0 0 8 0 0 7 9]
           [5 3 2 0 7 0 0 0 0
           6 0 0 1 9 5 0 0 0
           0 9 8 0 0 0 0 6 0
           8 0 0 0 6 0 0 0 3
           4 0 0 8 0 3 0 0 1
           7 0 0 0 2 0 0 0 6
           0 6 0 0 0 0 2 8 0
           0 0 0 4 1 9 0 0 5
           0 0 0 0 8 0 0 7 9]
           [5 3 4 0 7 0 0 0 0
            6 0 0 1 9 5 0 0 0
            0 9 8 0 0 0 0 6 0
            8 0 0 0 6 0 0 0 3
            4 0 0 8 0 3 0 0 1
            7 0 0 0 2 0 0 0 6
            0 6 0 0 0 0 2 8 0
            0 0 0 4 1 9 0 0 5
            0 0 0 0 8 0 0 7 9])))))



