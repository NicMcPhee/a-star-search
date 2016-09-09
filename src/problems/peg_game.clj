(ns problems.peg-game)

(def board-state [1 1 1 1 1 1 1 1 1 1 1 1 1 1 0])

(def moves (hash-map 0 [[3 1] [5 2]]
                     1 [[6 3] [8 4]]
                     2 [[7 4] [9 5]]
                     3 [[0 1] [5 4] [10 6] [12 7]]
                     4 [[11 7] [13 8]]
                     5 [[0 2] [3 4] [12 8] [14 9]]
                     6 [[1 3] [8 7]]
                     7 [[2 4] [9 8]]
                     8 [[1 4] [6 7]]
                     9 [[2 5] [7 8]]
                     10 [[3 6] [12 11]]
                     11 [[4 7] [13 12]]
                     12 [[3 7] [5 8] [10 11] [14 13]]
                     13 [[4 8] [11 12]]
                     14 [[5 9] [12 13]]))





(defn make-children [board-state index]
  (loop [children []
         moves (get moves index)
         board-state board-state
         i 0 ]
    (if (not= i (count moves))
      (if (and (= 1 (get board-state moves[0])) (= 1 (get board-state moves[1])))
        (recur (conj children (assoc board-state moves[0] 0 moves[1] 0 index 1)) moves board-state (+ 1 i)))
      children)))


(defn children [board-state]
  (loop [board-state board-state
         index 0
         children []]
    (if (not= index 15)
      (if (= 0 (get board-state index))
        (conj children (make-children board-state index)))
      children)))


