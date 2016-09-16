(ns problems.peg-puzzle)

;board is the array of peg board values
;zeros is a list of indeces of the zeros in the array
;QUESTION: should we include n in the state?
;QUESTION: do we need zeros really?
(defrecord State [board])

;sums up array
(defn sum [board]
  (reduce + board))


(defn swap [board old-pos new-pos mid-pos]
  (let [old-value (get-in board old-pos)
        new-value (get-in board new-pos)]
    (assoc-in (assoc-in (assoc-in board old-pos new-value) mid-pos 0)
              new-pos old-value)))


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
  (let [j (for [x [index]
     y (endRowIndexList n)]
      (compare x y))]
  (+ (if (> (.indexOf j 0) -1) (.indexOf j 0) (.indexOf j -1)) 1)))


(defn firstTwoDiagonals [n]
  (concat (beginRowIndexList n) (map inc (beginRowIndexList n))))



(defn lastTwoDiagonals [n]
  (concat (endRowIndexList n) (map dec (endRowIndexList n))))



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



(defn legalL? [board index n]
  (let [row (rowOfIndex index n)]
    (and
      (< 2 index)
      (>= (- index (beginRowIndex row)) 2)
      (= 0 (nth board index))
      (= 1 (nth board (- index 1)))
      (= 1 (nth board (- index 2))))))


(defn legalUR? [board index n]
  (let [row (rowOfIndex index n)
        illegal (lastTwoDiagonals n)]
    (and
      (= (.indexOf (for [x [index]
            y illegal]
          (compare x y)) 0) -1)
      (= 0 (nth board index))
      (= 1 (nth board (+ (- index row) 1)))
      (= 1 (nth board (+ (- index (* 2 row)) 3))))))



(defn legalUL? [board index n]
  (let [row (rowOfIndex index n)
        illegal (firstTwoDiagonals n)]
    (and
      (= (.indexOf (for [x [index]
            y illegal]
          (compare x y)) 0) -1)
      (= 0 (nth board index))
      (= 1 (nth board (- index row)))
      (= 1 (nth board (+ (- index (* 2 row)) 1))))))



(defn legalLR? [board index n]
  (let [row (rowOfIndex index n)]
    (and
      (not= row n)
      (not= row (- n 1))
      (= 0 (nth board index))
      (= 1 (nth board (+ (+ index row) 1)))
      (= 1 (nth board (+ (+ index (* 2 row)) 3))))))


(defn legalLL? [board index n]
  (let [row (rowOfIndex index n)]
    (and
      (not= row n)
      (not= row (- n 1))
      (= 0 (nth board index))
      (= 1 (nth board (+ index row)))
      (= 1 (nth board (+ (+ index (* 2 row)) 1))))))



;; (defn children [state]
;;   (let
;;  (for [let [x (range (- (count :board state) 1)
;;     (if (legalL? :board state x )




(defn prefer-horizontal-cost [s t]
  (let [s-x (first (:zeros s))
        t-x (first (:zeros t))]
    (inc (Math/abs (- s-x t-x)))))


(endRowIndex 4)

(endRowIndexList 5)
(nth (endRowIndexList 5) 2)

(rowOfIndex 0 5)
(rowOfIndex 9 5)


(let [x (int-array [1, 1, 1, 0, 1,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1])]
  (legalR? x 3 5))


(defn rowOfIndex [index n]
  (let [j (for [x [index]
     y (endRowIndexList n)]
      (compare x y))]
  (+ (if (> (.indexOf j 0) -1) (.indexOf j 0) (.indexOf j -1)) 1)))


  (for [x (int-array [1, 1, 1, 0, 1,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1])
        y (range 14)]
  (if (= x 0) y "no"))
