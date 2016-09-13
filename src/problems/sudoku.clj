(ns problems.sudoku)

(defrecord State [board curr-position])

(defn legal? [board-size n]
  (and (<= 0 n) (< n board-size)))

(def values (set (range 1 10)))

(def rows [:y1 :y2 :y3 :y4 :y5 :y6 :y7 :y8 :y9])

(def cols [:x1 :x2 :x3 :x4 :x5 :x6 :x7 :x8 :x9])

(def filled-spaces [])





(def numBank (range 1 10))


(defn add [board position number numBank]
  (let [num (nth a (- number 1))]
    (assoc-in board position num)))

(add [[0 1 2] [0 0 0] [0 0 0]
      [3 4 5] [0 0 0] [0 0 0]
      [6 7 8] [0 0 0] [0 0 0]

      [0 1 2] [0 0 0] [0 0 0]
      [3 4 5] [0 0 0] [0 0 0]
      [6 7 8] [0 0 0] [0 0 0]

      [0 1 2] [0 0 0] [0 0 0]
      [3 4 5] [0 0 0] [0 0 0]
      [6 7 8] [0 0 0] [0 0 0]] [0 0] 4 numBank)
