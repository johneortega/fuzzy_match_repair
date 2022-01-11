import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

from sklearn.datasets import make_classification
from sklearn.ensemble import ExtraTreesClassifier

# Build a classification task using 3 informative features
X, y = make_classification(n_samples=1000,
                           n_features=10,
                           n_informative=3,
                           n_redundant=0,
                           n_repeated=0,
                           n_classes=2,
                           random_state=0,
                           shuffle=False)

# Build a forest and compute the feature importances
forest = ExtraTreesClassifier(n_estimators=250,
                              random_state=0)

forest.fit(X, y)
importances = forest.feature_importances_
std = np.std([tree.feature_importances_ for tree in forest.estimators_],
             axis=0)
indices = np.argsort(importances)[::-1]

# Print the feature ranking
print("Feature ranking:")

for f in range(X.shape[1]):
    print("%d. feature %d (%f)" % (f + 1, indices[f], importances[indices[f]]))

# Plot the feature importances of the forest
'''
plt.figure()
plt.title("Feature importances")
tree_feature_names = ['GB1','GB2','GB5','GB6','TknsInTWiggle','gb22','gb33','gb44','gb00','gb13']
plt.bar(range(X.shape[1]), importances[indices],
       color="r", yerr=std[indices], align="center")
#plt.xticks(range(X.shape[1]), indices)
plt.xticks(range(X.shape[1]), tree_feature_names)
plt.xlim([-1, X.shape[1]])
'''
# Plot the feature importances of the forest
plt.figure()
plt.title("Feature importances")
plt.barh(range(X.shape[1]), importances[indices],
       color="r", xerr=std[indices], align="center")
# If you want to define your own labels,
# change indices to a list of labels on the following line.
tree_feature_names = ['GB1','GB2','GB5','GB6','TknsInTWiggle','gb22','gb33','gb44','gb00','gb13']
plt.yticks(range(X.shape[1]), tree_feature_names)
plt.ylim([-1, X.shape[1]])
plt.savefig('feature_importances.png')
