(ns problems.sudoku)
;;OH BOY KIDS HERE WE GO
(defn distinct?
  "Returns true if no two of the arguments are ="
  [& more]
  (let [old (count more)
        set (into #{} more)
        new (count set)]
    (= new old)))

;;## validation top level ##
(defn every-element-in-vec-distinct?
 "Returns True if every element in every vector is unique or nil.
  Returns False otherwise."
  [vecs]
 (every? identity (map #(apply distinct? %) vecs)))

(def every-vector-valid? (memoize every-element-in-vec-distinct?))

;;## validating 3x3 blocks ##
(defn hop [adjusted-n k chunk]
  "Given the first number in the block (n), and the offset (k), returns the 3
  numbers that make up a column in a sudoku block."
  (take-nth 9 (drop (+ adjusted-n k) chunk)))

(defn hop-block [chunk n]
  "Gets the three columns in a 3x3 sudoku block."
  (let [adjusted-n (* n 3)] ;; the upper left hand number in a sudoku block.
    (into [] (concat (hop adjusted-n 0 chunk)
                     (hop adjusted-n 1 chunk)
                     (hop adjusted-n 2 chunk)))))

(defn get-blocks [chunk]
  "Operates on a 3x9 chunk of a sudoku state. Returns a list of 3 vectors of
  9 elements that represents a single block (3x3) in sudoku."
  (map #(hop-block chunk %) [0 1 2])) ;; the 'numbers' of the three blocks

(defn check-squares? [state]
  "Checks that every 3x3 sudoku block has contains distinct elements or nil."
  (every-vector-valid? (mapcat get-blocks (partition 27 state))))

;;validate horizontal
(defn check-horizontal?
 "Check that each row contains distinct elements"
 [state]
 (every-vector-valid? (partition 9 state)))

(defn do-children
  "Given a sudoku state, a vector of ints, returns all valid child states as a vector of ints"
  [state]
 state)

;;validate vertical
(defn do-vertical-valid [coll state]
  (take-nth 9 (drop coll state)))

(defn check-vertical? [state]
  (every-vector-valid? (map #(do-vertical-valid % state) (range 9))))

;;all together now
(defn valid-state?
  "does the validation"
  [state] (and (check-squares? state)
               (check-horizontal? state)
               (check-vertical? state)))

;;blank space stuff
(defn b "makes a unique string." [] (str (gensym)))

(defn make-next-states
  "returns a list of states"
  [state] (let [front (take-while number? state)
                back (drop (+ 1 (count front)) state)]
            (map #(concat front [%] back) (range 1 10))))


;; children
(defn do-children
  "makes children states"
  [state] (filter valid-state? (make-next-states state)))




(def children (memoize do-children))




(def test-vec
  [nil nil nil nil nil nil nil 4 nil
   nil nil nil nil nil nil nil 4 nil])

(def test-vec-2
  [nil nil nil nil nil nil nil nil nil
   nil nil nil nil nil nil 4   4   nil])

(def test-vec-3 [1 2 3 4 5 6 7 8 9
                 1 2 3 4 5 6 7 8 9
                 1 2 3 4 5 6 7 8 9])

(def test-chunk [ 0  1  2  3  4  5  6  7  8
                  9 10 11 12 13 14 15 16 17
                 18 19 20 21 22 23 24 25 26])

(def test-block [ 0  1  2  3  4  5  6  7  8
                  9 10 11 12 13 14 15 16 17
                 18 19 20 21 22 23 24 25 26
                 27 28 29 30 31 32 33 34 35
                 36 37 38 39 40 41 42 43 44
                 45 46 47 48 49 50 51 52 53
                 54 55 56 57 58 59 60 61 62
                 63 64 65 66 67 68 69 70 71
                 72 73 74 75 76 77 78 79 80])

(def test-bad   [ 0  1  2  3  4  5  6  7  8
                  0  1  2 12 13 14 15 16 17
                  0  1  2 21 22 23 24 25 26
                 27 28 29 30 31 32 33 34 35
                 36 37 38 39 40 41 42 43 44
                 45 46 47 48 49 50 51 52 53
                 54 55 56 57 58 59 60 61 62
                 63 64 65 66 67 68 69 70 71
                 72 73 74 75 76 77 78 79 80])
