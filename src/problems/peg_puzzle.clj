(ns problems.peg-puzzle)

;board is the array of peg board values
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
(defn legalBoard? [proposed-board proposed-n]
  (and (<= 3 proposed-n) (= (count proposed-board) (reduce + (range (+ 1 proposed-n))))))



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
  (if (legalBoard? (:board state) (:n state))
    (let [b (:board state)
        n (:n state)
        z (zeros b)
        rightList (map #(jumpR b % n) z)
        leftList (map #(jumpL b % n) z)
        upperRightList (map #(jumpUR b % n) z)
        upperLeftList (map #(jumpUL b % n) z)
        lowerRightList (map #(jumpLR b % n) z)
        lowerLeftList (map #(jumpLL b % n) z)
        listList (concat rightList leftList upperRightList upperLeftList lowerRightList lowerLeftList)
        finalList (remove nil? listList)
        ]

(map #(->State % n) finalList))))


(let [x (->State [0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] 5)]
  (children x))

