(ns n_puzzle_as_search.midje
  (:use [midje.sweet])
  (:require [n-puzzle-as-search.state :refer :all]))

(fact (+ 2 2) => 4)
(fact (+ 2 2) => even?)

(facts
  (:board (->State [0 1 2 3 4 5 6 7 8] [0 0]))
      => [0 1 2 3 4 5 6 7 8]
  (:board (->State [5 4 9 6 3 2 0 1 7 8] [2 0]))
      => [5 4 9 6 3 2 0 1 7 8]
  )

(facts
  (:blank-position (->State [0 1 2 3 4 5 6 7 8] [0 0]))
      => [0 0]
  (:blank-position (->State [5 4 6 3 2 0 1 7 8] [1 2]))
      => [1 2]
  )

(facts
  (swap [0 1 2 3 4 5 6 7 8] [0 1])
    => [1 0 2 3 4 5 6 7 8]
  (swap [0 1 2 3 4 5 6 7 8] [1 0])
    => [3 1 2 0 4 5 6 7 8]
  (swap [5 4 6 3 2 0 1 7 8] [0 2])
    => [5 4 0 3 2 6 1 7 8]
  (swap [5 4 6 3 2 0 1 7 8] [2 2])
    => [5 4 6 3 2 8 1 7 0]
  (swap [5 4 6 3 2 0 1 7 8] [1 1])
    => [5 4 6 3 0 2 1 7 8]
  )

(future-fact
  (children (->State [0 1 2 3 4 5 6 7 8] [0 0]))
    => [(->State [1 0 2 3 4 5 6 7 8] [0 1])
        (->State [3 1 2 0 4 5 6 7 8] [1 0])])
