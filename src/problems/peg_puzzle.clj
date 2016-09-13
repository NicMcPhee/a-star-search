(ns problems.peg-puzzle)

(defrecord State [board zeros])

(defn legalBoard? [board-size n]
  (and (<= 3 n) (< n board-size)))

(defn legalR? [index n]
  (and (<= 0 index) ()))

(defn sum [board] ;sums up array
  (reduce + board))

(defn swap [board old-pos new-pos mid-pos]
  (let [old-value (get-in board old-pos)
        new-value (get-in board new-pos)]
    (assoc-in (assoc-in (assoc-in board old-pos new-value) mid-pos 0)
              new-pos old-value)))

(defn children [state]
  (let [x (first (:zeros state))
        y (second (:zeros state))
        board-size (count (:board state))]
  (for [[dx dy] [[-1, 0] [1, 0], [0, -1], [0, 1]]
        :let [new-x (+ x dx)
              new-y (+ y dy)]
        :when (legal? board-size new-x)
        :when (legal? board-size new-y)]
    (->State (swap (:board state) [x y] [new-x new-y])
             [new-x new-y]))))

(defn prefer-horizontal-cost [s t]
  (let [s-x (first (:zeros s))
        t-x (first (:zeros t))]
    (inc (Math/abs (- s-x t-x)))))


(defn beginRowIndex [row] ;returns index at beginning of row
  (let [x row]
    (reduce + (take x (iterate (fn [n](- n 1)) (- x 1))))))

(defn beginRowIndexList [n]
  (map beginRowIndex (range 1 (+ 1 n))))

(defn endRowIndex [row] ;returns index at end of row
  (let [x row]
    (reduce + (take (- x 1) (iterate (fn [n](- n 1)) x)))))

(defn endRowIndexList [n]
  (map endRowIndex (range 1 (+ 1 n))))

(defn rowOfIndex [index n]
  (filter (endRowIndexList n)))

(endRowIndexList 5)
(beginRowIndexList 5)
