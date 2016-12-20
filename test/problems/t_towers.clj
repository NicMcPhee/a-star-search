(ns problems.t-towers
  (:use [midje.sweet])
  (:require [problems.towers :refer :all]))



(facts
  ((:vec-of-pegs (->State [[] [1] [5]])) 0) => []
  ((:vec-of-pegs (->State [[] [1] [5]])) 1) => [1]
  ((:vec-of-pegs (->State [[] [1] [5]])) 2) => [5]
  ((:vec-of-pegs (->State [[] [] []])) 0) => [])

;;(facts
;;  (->State [[1 2 3] [] ["doot"]]) => "Fail to see state record!")

(facts
  (legal? (->State [[] [] []])) => true
  (legal? (->State [[1] [] []])) => true
  (legal? (->State [[] [1] []])) => true
  (legal? (->State [[] [] [1]])) => true
  (legal? (->State [[1] [2] []])) => true
  (legal? (->State [[1] [2] [3]])) => true
  (legal? (->State [[1] [2] [4 3]])) => true
  (legal? (->State [[1] [2] [5 4 3]])) => true
  (legal? (->State [[1] [2] [4 4 3]])) => false
  (legal? (->State [[] [2 1] [5 4 3]])) => true
  (legal? (->State [[1] [] [2 3]])) => false
  (legal? (->State [[1 2 3 4 5] [] []])) => false
  (legal? (->State [[1 2 3 4 5] [1] []])) => false
  (legal? (->State [[1 2 3 4 5 5] [] [55]])) => false
  )

;; Tests for children-by-peg
(facts
  (children-by-peg 0 (->State [[3 2 1] [] []])) => (list (->State [[3 2] [1] []]) (->State [[3 2] [] [1]]))
)

;; Tests for generate-children
(facts
	(children (->State [[3 2 1] [] []])) => (list (->State [[3 2] [1] []])
								(->State [[3 2] [] [1]]))

	(children (->State [[3 1] [2] []])) => (list (->State [[3] [2 1] []])
								(->State [[3] [2] [1]])
								(->State [[3 1] [] [2]]))
)

;; Tests for fitness
(facts
  (fitness-lonely-disk (->State [[1 2] [] [3]])) => 1
  (fitness-lonely-disk (->State [[1 2 3 4] [2] [] [] [5 6] [9]])) => 2

  (fitness-diff (->State [[1 2 3 4] [] []]) (->State [[1 2 3 4] [] []])) => 0
  (fitness-diff (->State [[1 2 3 4] [] []]) (->State [[1 2 3] [4] []])) => 2
  (fitness-diff (->State [[1 2 3 4] [] []]) (->State [[1 2] [3] [4]])) => 4
)
