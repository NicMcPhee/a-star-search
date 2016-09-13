(ns problems.triangle)

; Solve the triangle puzzlie with search.

(defrecord State [board])

(defn get-all-coords [state]
   (apply concat
          (map
            (fn [index]
              (map #(conj [index] %) (range (inc index))))
            (range (count state)))))

(def coords (get-all-coords [[][][][][]])) ;; Shhh, it's fine!


(defn my-get [row col board]
  (nth (nth board row) col))

(defn end-point-on-board?
  "Takes the index of the last row in the board and a jump pair
    and returns true iff the end point is on the board."
  [last-row-index {[row col] :end}]
  (not  (or
         ;; check for running off the left end of the row
         ;; (> 0 (second end))
         (> 0 col)

         ;; check for running off the right end of the row
         (> col row)

         ;; check for going down too far
         (< last-row-index row)

         ;; check for going up too far
         (> 0 row)
         )))

(defn get-adj-coords
  "Takes a row and column and returns a vector of positions that you
   can jump to paired with the position that you will jump over.
   This function assumes that your grid is infinite so you will have
   to filter the points returned."
  [[row column]]
  [{:over [row (+ column 1)] :end [row (+ column 2)]}
   {:over [row (- column 1)] :end [row (- column 2)]}
   {:over [(+ row 1) column] :end [(+ row 2) column]}
   {:over [(+ row 1) (+ column 1)] :end [(+ row 2) (+ column 2)]}
   {:over [(- row 1) column] :end [(- row 2) column]}
   {:over [(- row 1) (- column 1)] :end [(- row 2) (- column 2)]}])

(defn generate-valid-states [[row col] state]
  (if (= (my-get row col state) 0)
    []
    (filter
      #(and (end-point-on-board? (dec (count state)) %)
            (= (my-get
                 (first (:end %))
                 (second (:end %))
                  state)
               0)
            (= (my-get
                 (first (:over %))
                 (second (:over %))
                  state)
               1))
      (get-adj-coords [row col]))))

(defn do-jump [state start jump]
  (assoc-in state stat
  )


(defn children [state]
  (apply concat (map #(generate-valid-states % state) coords)))




(defn filled?
  "Takes a row, column, and board and returns
   true if the board has a peg (1) there."
  [row col board]
  )



(def x [[1] [1 1] [1 1 1] [1 1 1 1] [1 1 1 1 0]])

(assoc-in x [2 1] 7)
; (my-get 4 3 x)
; (into [3] [2])
; (conj [1] 3)
; (apply concat (children x))

coords
