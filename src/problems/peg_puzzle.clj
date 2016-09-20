(ns problems.peg-puzzle)

;board is the array of peg board values
;zeros is a list of indeces of the zeros in the array
;QUESTION: should we include n in the state?
;QUESTION: do we need zeros really?
(defrecord State [board n])

;sums up array
(defn sum [board]
  (reduce + board))


(defn swap [board old-pos mid-pos new-pos]
  (let [old-value (get board old-pos)
        new-value (get board new-pos)]
    (assoc (assoc (assoc board old-pos new-value) mid-pos 0)
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

(defn zeros [board]
  (filter #(= 0 (nth board %))
        (range (count board))))

(defn jumpR [board index n]
  (if (legalR? board index n) (swap board index (+ index 1) (+ index 2))))

(defn jumpL [board index n]
 (if (legalL? board index n) (swap board index (- index 1) (- index 2))))

(defn jumpUL [board index n]
  (let [row (rowOfIndex index n)]
   (if (legalUL? board index n) (swap board index (- index row) (+ (- index (* 2 row)) 1)))))

(defn jumpUR [board index n]
  (let [row (rowOfIndex index n)]
   (if (legalUR? board index n) (swap board index (+ (- index row) 1) (+ (- index (* 2 row)) 3)))))

(defn jumpLL [board index n]
  (let [row (rowOfIndex index n)]
   (if (legalLL? board index n) (swap board index (+ index row) (+ (+ index (* 2 row)) 1)))))

(defn jumpLR [board index n]
  (let [row (rowOfIndex index n)]
   (if (legalLR? board index n) (swap board index (+ (+ index row) 1) (+ (+ index (* 2 row)) 3)))))

;start with boardList empty
;add all jumps to boardList
;then child runs the entire boardList into states(just have to call child at end)
(defn children[state]
  (let [b (:board state)
        n (:n state)
        z (zeros b)
        boardList (map #(jumpR b % n) z)
        child (map #(->State % n) boardList)]

    (map #(conj boardList %) leftList)

boardList))

;;     into boardList (map #(jumpL b % n) z))

;;  (map #(->State (% n) boardList)

(defn prefer-horizontal-cost [s t]
  (let [s-x (first (:zeros s))
        t-x (first (:zeros t))]
    (inc (Math/abs (- s-x t-x)))))


(endRowIndex 4)

(endRowIndexList 5)
(nth (endRowIndexList 5) 2)

(rowOfIndex 0 5)
(rowOfIndex 9 5)
(let [x (->State [1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1] 5)]
  (children x))


(jumpLR [1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1] 3 5)

;;IMPORTANT!!!!!!! DAMNIT THIS IS IMPORTANT DON'T DELETE
(for [i (range 15)
      :when (= 1 (nth [1, 1, 1, 0, 1,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1] i))]
  i)

;;# is an annonymous function (build your own function inside of another function)
;;% is the argument for that function (%1 is arg 1, %2 is arg 2
(filter #(= 0 (nth [1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1] %))
        (range 15))

(filter (fn [index] (= 0 (nth [1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1] index)))
        (range 15))

(map #(+ %1 %2) [5 8 9] [6 3 2])

(map + [5 8 9] [6 3 2])

(map #(/ (+ %1 %2) 2) [5 8 9] [2 3 6])
