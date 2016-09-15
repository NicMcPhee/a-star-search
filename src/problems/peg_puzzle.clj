(ns problems.peg-puzzle)

;board is the array of peg board values
;zeros is a list of indeces of the zeros in the array
;QUESTION: should we include n in the state?
;QUESTION: do we need zeros really?
(defrecord State [board zeros])

;sums up array
(defn sum [board]
  (reduce + board))

;n is number of rows
(defn legalBoard? [proposed-n]
  (<= 3 proposed-n))

;NOTE: board might not be "board" here, it should be the current state of the board
(defn legalR? [board index n]
  (let [row (rowOfIndex index n)]
    (and
      (< 2 index)
      (>= (- (endRowIndex row) index) 2)
      (= 0 (nth board index))
      (= 1 (nth board (+ index 1)))
      (= 1 (nth board (+ index 2))))))

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
        :when (legalBoard? board-size new-x)
        :when (legalBoard? board-size new-y)]
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

;returns index at end of row
(defn endRowIndex [row]
  (let [x row]
    (reduce + (take (- x 1) (iterate (fn [n](- n 1)) x)))))

(defn endRowIndexList [n]
  (map endRowIndex (range 1 (+ 1 n))))

(defn rowOfIndex [index n]
  (for [x [index]
     y (endRowIndexList n)]
      (compare x y))
  (let [j (for [x [index]
     y (endRowIndexList n)]
      (compare x y))]
  (+ (if (> (.indexOf j 0) -1) (.indexOf j 0) (.indexOf j -1)) 1)))

(endRowIndex 4)

(endRowIndexList 5)
(nth (endRowIndexList 5) 2)

(rowOfIndex 0 5)
(rowOfIndex 9 5)

(let [x (int-array [0, 1, 1, 0, 1,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1])]
  (legalR? x 3 5))
