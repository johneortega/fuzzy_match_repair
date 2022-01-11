#!/usr/bin/env python
# encoding: utf-8
'''
learn_model -- Program that learns machine translation quality estimation
models

learn_model is a program with which is possible to learn models for
sentence-pair quality estimation models using the algorithms implemented in the
scikit-learn machine learning toolkit.

It defines functions to work with different machine learning algorithms as well
as feature selection techniques and features preprocessing. The only dependency
so far is the sklearn package. ConfigParser is used to parse the configuration
file which has a similar layout to the Java properties file.

@author:     Jose' de Souza
        
@copyright:  2012. All rights reserved.
        
@license:    Apache License 2.0

@contact:    jose.camargo.souza@gmail.com
@deffield    updated: Updated
'''

from argparse import ArgumentParser, RawDescriptionHelpFormatter
from evaluation_measures import root_mean_squared_error, mean_absolute_error
from sklearn import datasets, linear_model
from sklearn.ensemble.forest import ExtraTreesClassifier
from sklearn.ensemble.forest import ExtraTreesRegressor
from sklearn.grid_search import GridSearchCV
from sklearn.linear_model.coordinate_descent import LassoCV
from sklearn.linear_model.least_angle import LassoLarsCV, LassoLars
from sklearn.linear_model.randomized_l1 import RandomizedLasso
#from sklearn.metrics.metrics import mean_squared_error, f1_score, \
#    precision_score, recall_score
from sklearn.metrics import mean_squared_error, f1_score, \
    precision_score, recall_score
from sklearn.svm.classes import SVR, SVC
from sklearn_utils import scale_datasets, open_datasets, assert_number, \
    assert_string
import logging as log
import numpy as np
np.set_printoptions(threshold='nan')
import os
import sys
import yaml
from sklearn import tree
import graphviz

from customize_scorer import pearson_corrcoef, binary_precision, classify_report_bin, classify_report_bin_regression, classify_report_regression
from sklearn.feature_selection import RFE
from sklearn.linear_model import LogisticRegression
import pickle
from sklearn import svm,datasets

__all__ = []
__version__ = 0.1
__date__ = '2012-11-01'
__updated__ = '2012-11-01'

DEBUG = 0
PROFILE = 0

DEFAULT_SEP = "\t"

class CLIError(Exception):
    '''Generic exception to raise and log different fatal errors.'''
    def __init__(self, msg):
        super(CLIError).__init__(type(self))
        self.msg = "E: %s" % msg
    def __str__(self):
        return self.msg
    def __unicode__(self):
        return self.msg

'''
def plot_svc_decision_function(model, ax=None, plot_support=True):
    """Plot the decision function for a 2D SVC"""
    if ax is None:
        ax = plt.gca()
    xlim = ax.get_xlim()
    ylim = ax.get_ylim()
    
    # create grid to evaluate model
    x = np.linspace(xlim[0], xlim[1], 30)
    y = np.linspace(ylim[0], ylim[1], 30)
    Y, X = np.meshgrid(y, x)
    xy = np.vstack([X.ravel(), Y.ravel()]).T
    P = model.decision_function(xy).reshape(X.shape)
    
    # plot decision boundary and margins
    ax.contour(X, Y, P, colors='k',
               levels=[-1, 0, 1], alpha=0.5,
               linestyles=['--', '-', '--'])
    
    # plot support vectors
    if plot_support:
        ax.scatter(model.support_vectors_[:, 0],
                   model.support_vectors_[:, 1],
                   s=300, linewidth=1, facecolors='none');
    ax.set_xlim(xlim)
    ax.set_ylim(ylim)
'''


def save_feature_pic(forest,X):
    import numpy as np
    import matplotlib
    matplotlib.use('Agg')
    import matplotlib.pyplot as plt

    importances = forest.feature_importances_
    std = np.std([tree.feature_importances_ for tree in forest.estimators_],
                 axis=0)
    indices = np.argsort(importances)[::-1]

    # Print the feature ranking
    print("Feature ranking:")
    print("indices" + str(indices))
    print("importances" + str(importances))
    print("x shape" + str(X.shape[1]))
    print("range x shape" + str(range(X.shape[1])))
    tree_feature_names = ['BB1','BB2','BB3','BB4','BB5','BB6','GB1','GB2','GB3','GB4','GB5','GB6','GB7','GB8','GB9','GB10','GB11','GB12','tsize', 'GB14','GB15','#po','tkn_sp','tkn_tw','punc_sp','punc_tw','ground_1','ground_2','pnctw/pncsp','digtw/digsp', 'numdigspr', 'numdigtw', 'numtoktwig/numtokspr']
    #tree_feature_names = ['BB1','BB2','BB3','BB4','BB5','BB6','GB1','GB2','GB3','GB4','GB5','GB6','GB7','GB8','GB9','GB10','GB11','GB12','tsize', 'GB14','GB15','#po','tkn_sp','tkn_tw','punc_sp','punc_tw','ground_1','ground_2','pnctw/pncsp','digtw/digsp', 'numdigspr', 'numdigtw', 'numtoktwig/numtokspr']
    #tree_feature_names = ['BB2','BB3','BB5','BB6','GB1','GB2','GB3','GB4','GB5','GB6','GB7','GB8','GB9','GB10','GB11','GB12','tsize', 'GB14','GB15','#po','tkn_sp','tkn_tw','punc_sp','punc_tw','ground_1','ground_2','pnctw/pncsp','digtw/digsp']
    print("tree feature index" + tree_feature_names[33])
    final_tree_names=[]
    for f in range(X.shape[1]):
        print("%d. feature %d (%f)" % (f + 1, indices[f], importances[indices[f]]))
        final_tree_names.append(tree_feature_names[indices[f]])
        print("%d. %s (%f)" % (f + 1, tree_feature_names[indices[f]], importances[indices[f]]))

    print("exiting from feature save")
    sys.exit()


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
    #plt.yticks(range(X.shape[1]), indices)
    #plt.yticks(range(X.shape[1]), tree_feature_names)
    plt.yticks(range(X.shape[1]), final_tree_names)
    plt.ylim([-1, X.shape[1]])
    plt.savefig('feature_importances.png')

def make_meshgrid(x, y, h=.02):
    """Create a mesh of points to plot in

    Parameters
    ----------
    x: data to base x-axis meshgrid on
    y: data to base y-axis meshgrid on
    h: stepsize for meshgrid, optional

    Returns
    -------
    xx, yy : ndarray
    """
    #x_min, x_max = x.min() - 1, x.max() + 1
    #y_min, y_max = y.min() - 1, y.max() + 1
    x_min, x_max = x.min() - 0.005, x.max() + 0.005
    y_min, y_max = y.min() - 0.005, y.max() + 0.005
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                         np.arange(y_min, y_max, h))
    return xx, yy


def plot_contours(ax, clf, xx, yy, **params):
    """Plot the decision boundaries for a classifier.

    Parameters
    ----------
    ax: matplotlib axes object
    clf: a classifier
    xx: meshgrid ndarray
    yy: meshgrid ndarray
    params: dictionary of params to pass to contourf, optional
    """
    Z = clf.predict(np.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)
    print ax
    out = ax.contourf(xx, yy, Z, **params)
    return out

def save_scatter_mesh(X, y, clf):
    import numpy as np
    import matplotlib
    matplotlib.use('Agg')
    import matplotlib.pyplot as plt



    X = X[:, [0, 1]] #gives us column 0 and 1 from the original
    # title for the plots
    title = 'some rbf'
    # Set-up 2x2 grid for plotting.
    fig, sub = plt.subplots(2, 2)
    plt.subplots_adjust(wspace=0.4, hspace=0.4)
    clf = clf.fit(X, y) #fit the model
    X0, X1 = X[:, 0], X[:, 1]
    xx, yy = make_meshgrid(X0, X1)
    #ax = zip(sub.flatten())# zip necessary for tuples
    models = []
    titles = []
    models.append(clf)
    titles.append(title)
    
    for clf, title, ax in zip(models, titles, sub.flatten()):
        plot_contours(ax, clf, xx, yy, cmap=plt.cm.coolwarm, alpha=0.8)
        ax.scatter(X0, X1, c=y, cmap=plt.cm.coolwarm, s=20, edgecolors='k')
        ax.set_xlim(xx.min(), xx.max())
        ax.set_ylim(yy.min(), yy.max())
        ax.set_xlabel('Sepal length')
        ax.set_ylabel('Sepal width')
        ax.set_xticks(())
        ax.set_yticks(())
        ax.set_title(title)
        plt.savefig('scatter_mesh.png')
def test_mesh():
    import numpy as np
    import matplotlib.pyplot as plt

    origin = 'lower'
    #origin = 'upper'

    delta = 0.025

    x = y = np.arange(-3.0, 3.01, delta)
    X, Y = np.meshgrid(x, y)
    Z1 = plt.mlab.bivariate_normal(X, Y, 1.0, 1.0, 0.0, 0.0)
    Z2 = plt.mlab.bivariate_normal(X, Y, 1.5, 0.5, 1, 1)
    Z = 10 * (Z1 - Z2)

    nr, nc = Z.shape

    # put NaNs in one corner:
    Z[-nr//6:, -nc//6:] = np.nan
    # contourf will convert these to masked


    Z = np.ma.array(Z)
    # mask another corner:
    Z[:nr//6, :nc//6] = np.ma.masked

    # mask a circle in the middle:
    interior = np.sqrt((X**2) + (Y**2)) < 0.5
    Z[interior] = np.ma.masked

    # We are using automatic selection of contour levels;
    # this is usually not such a good idea, because they don't
    # occur on nice boundaries, but we do it here for purposes
    # of illustration.
    CS = plt.contourf(X, Y, Z, 10,
                      #[-1, -0.1, 0, 0.1],
                      #alpha=0.5,
                      cmap=plt.cm.bone,
                      origin=origin)

    # Note that in the following, we explicitly pass in a subset of
    # the contour levels used for the filled contours.  Alternatively,
    # We could pass in additional levels to provide extra resolution,
    # or leave out the levels kwarg to use all of the original levels.

    CS2 = plt.contour(CS, levels=CS.levels[::2],
                      colors='r',
                      origin=origin)

    plt.title('Nonsense (3 masked regions)')
    plt.xlabel('word length anomaly')
    plt.ylabel('sentence length anomaly')

    # Make a colorbar for the ContourSet returned by the contourf call.
    cbar = plt.colorbar(CS)
    cbar.ax.set_ylabel('verbosity coefficient')
    # Add the contour line levels to the colorbar
    cbar.add_lines(CS2)

    plt.figure()

    # Now make a contour plot with the levels specified,
    # and with the colormap generated automatically from a list
    # of colors.
    levels = [-1.5, -1, -0.5, 0, 0.5, 1]
    CS3 = plt.contourf(X, Y, Z, levels,
                       colors=('r', 'g', 'b'),
                       origin=origin,
                       extend='both')
    # Our data range extends outside the range of levels; make
    # data below the lowest contour level yellow, and above the
    # highest level cyan:
    CS3.cmap.set_under('yellow')
    CS3.cmap.set_over('cyan')

    CS4 = plt.contour(X, Y, Z, levels,
                      colors=('k',),
                      linewidths=(3,),
                      origin=origin)
    plt.title('Listed colors (3 masked regions)')
    plt.clabel(CS4, fmt='%2.1f', colors='w', fontsize=14)

    # Notice that the colorbar command gets all the information it
    # needs from the ContourSet object, CS3.
    plt.colorbar(CS3)

    # Illustrate all 4 possible "extend" settings:
    extends = ["neither", "both", "min", "max"]
    cmap = plt.cm.get_cmap("winter")
    cmap.set_under("magenta")
    cmap.set_over("yellow")
    # Note: contouring simply excludes masked or nan regions, so
    # instead of using the "bad" colormap value for them, it draws
    # nothing at all in them.  Therefore the following would have
    # no effect:
    # cmap.set_bad("red")

    fig, axs = plt.subplots(2, 2)
    fig.subplots_adjust(hspace=0.3)

    for ax, extend in zip(axs.ravel(), extends):
        cs = ax.contourf(X, Y, Z, levels, cmap=cmap, extend=extend, origin=origin)
        fig.colorbar(cs, ax=ax, shrink=0.9)
        ax.set_title("extend = %s" % extend)
        ax.locator_params(nbins=4)


def save_scatter_pic(X, y, y_rbf):
    import matplotlib
    matplotlib.use('Agg')
    import matplotlib.pyplot as plt
    print("Saving Scatter Plot")
    lw = 2
    #print "The size is: " + str(X[:,0].shape) + " and y:" + str(y.shape)
    ### for printing when you have bb1 and bb4
    #print "This is BB1 array: " + str(X[:,0])
    #plt.scatter(X[:,0], y, color='orange', label='BB1')
    #plt.scatter(X[:,1], y, color='red', label='BB2')
    #plt.scatter(X[:,2], y, color='blue', label='BB3')
    #plt.scatter(X[:,3], y, color='pink', label='BB4')
    #plt.scatter(X[:,18], y, color='green', label='SIZE OF t')
    #plt.scatter(X[:,29], y, color='purple', label="NUM_DIG_T~/NUM_DIG_S'")
    ### for printing when you don't have bb1 and bb4
    #plt.scatter(X[:,0], y, color='red', label='BB2')
    #plt.scatter(X[:,1], y, color='blue', label='BB3')
    plt.scatter(X[:,16], y, color='green', label='SIZE OF t')
    #plt.scatter(X[:,27], y, color='purple', label="NUM_DIG_T~/NUM_DIG_S'")

    #plt.scatter(X[:,3], y, color='red', label='feature_4')
    #plt.plot(X[:,0], y_rbf, color='navy', lw=lw, label='RBF model Feature 1')
    #plt.plot(X[:,1], y_rbf, color='navy', lw=lw, label='RBF model Feature 2')
    #plt.plot(X[:,0], y_rbf, color='navy', lw=lw, label='RBF model BB2')
    #plt.plot(X[:,1], y_rbf, color='navy', lw=lw, label='RBF model BB3')
    #plt.plot(X[:,3], y_rbf, color='navy', lw=lw, label='RBF model Feature 4')
    #plt.plot(X[:,18], y_rbf, color='navy', lw=lw, label='RBF model SIZE OF t')
    plt.plot(X[:,16], y_rbf, color='navy', lw=lw, label='RBF model SIZE OF t')
    #plt.plot(X, y_lin, color='c', lw=lw, label='Linear model')
    #plt.plot(X, y_poly, color='cornflowerblue', lw=lw, label='Polynomial model')
    plt.xlabel('data')
    plt.ylabel('target')
    plt.title('Support Vector Regression')
    plt.legend(loc='lower right', prop={'size':6}, bbox_to_anchor=(1,1))
    #plt.legend()
    plt.savefig('svr_scatter_plot.png')
def set_selection_method(config):
    """
    Given the configuration settings, this function instantiates the configured
    feature selection method initialized with the preset parameters.
    
    TODO: implement the same method using reflection (load the class dinamically
    at runtime)
    
    @param config: the configuration file object loaded using yaml.load()
    @return: an object that implements the TransformerMixin class (with fit(),
    fit_transform() and transform() methods).
    """
    transformer = None
    
    selection_cfg = config.get("feature_selection", None)
    if selection_cfg:
        method_name = selection_cfg.get("method", None)
        
        # checks for RandomizedLasso
        if method_name == "RandomizedLasso":
            p = selection_cfg.get("parameters", None)
            if p:
                transformer = \
                RandomizedLasso(alpha=p.get("alpha", "aic"), 
                                scaling=p.get("scaling", .5), 
                                sample_fraction=p.get('sample_fraction', .75), 
                                n_resampling=p.get('n_resampling', 200),
                                selection_threshold=p.get('selection_threshold', .25), 
                                fit_intercept=p.get('fit_intercept', True), 
                                # TODO: set verbosity according to global level
                                verbose=True, 
                                normalize=p.get('normalize', True), 
                                max_iter=p.get('max_iter', 500), 
                                n_jobs=p.get('n_jobs', 1))
            else:
                transformer = RandomizedLasso()
        
        # checks for ExtraTreesClassifier
        elif method_name == "ExtraTreesClassifier":
            p = selection_cfg.get("parameters", None)
            if p:
                transformer = \
                ExtraTreesClassifier(n_estimators=p.get('n_estimators', 10),
                                     max_depth=p.get('max_depth', None),
                                     min_samples_split=p.get('min_samples_split', 1),
                                     min_samples_leaf=p.get('min_samples_leaf', 1),
                                     #min_density=p.get('min_density', 1),
                                     max_features=p.get('max_features', 'auto'),
                                     bootstrap=p.get('bootstrap', False),
                                     #compute_importances=p.get('compute_importances', True),
                                     n_jobs=p.get('n_jobs', 1),
                                     random_state=p.get('random_state', None),
                                     # TODO: set verbosity according to global level
                                     verbose=True)
            else:
                transformer = ExtraTreesClassifier()
        # checks for ExtraTreesRegressor
        elif method_name == "ExtraTreesRegressor":
            p = selection_cfg.get("parameters", None)
            if p:
                transformer = \
                ExtraTreesRegressor(n_estimators=100, verbose=True)
		'''
                transformer = \
                ExtraTreesRegressor(n_estimators=p.get('n_estimators', 10),
                                     max_depth=p.get('max_depth', None),
                                     #min_samples_split=p.get('min_samples_split', 1),
                                     min_samples_split=p.get('min_samples_split', 2),
                                     min_samples_leaf=p.get('min_samples_leaf', 1),
                                     #min_density=p.get('min_density', 1),
                                     max_features=p.get('max_features', 'auto'),
                                     bootstrap=p.get('bootstrap', False),
                                     #compute_importances=p.get('compute_importances', True),
                                     n_jobs=p.get('n_jobs', 1),
                                     random_state=p.get('random_state', None),
                                     # TODO: set verbosity according to global level
                                     verbose=True)
		'''
            else:
                transformer = \
                ExtraTreesRegressor(n_estimators=100, verbose=True)
        # checks for RFE
        elif method_name == "RFE":
            p = selection_cfg.get("parameters", None)
            if p:
		model = LogisticRegression()
                transformer = \
			rfe = RFE(model, 3)
            else:
                transformer = ExtraTreesClassifier()
	print "the transformer is: " + str(transformer)

    return transformer


def set_scorer_functions(scorers):
    scores = []
    for score in scorers:
        if score == 'mae':
            scores.append((score, mean_absolute_error))
        elif score == 'rmse':
            scores.append((score, root_mean_squared_error))
        elif score == 'mse':
            scores.append((score, mean_squared_error))
        elif score == 'f1_score':
            scores.append((score, f1_score))
        elif score == 'precision_score':
            scores.append((score, precision_score))
        elif score == 'recall_score':
            scores.append((score, recall_score))
        elif score == 'pearson_corrcoef':
            scores.append((score, pearson_corrcoef))
        elif score == 'binary_precision':
            scores.append((score, binary_precision))
            
    return scores


def set_optimization_params(opt):
    params = {}
    for key, item in opt.items():
        # checks if the item is a list with numbers (ignores cv and n_jobs params)
        if isinstance(item, list) and (len(item) == 3) and assert_number(item):
            # create linear space for each parameter to be tuned
            params[key] = np.linspace(item[0], item[1], num=item[2], endpoint=True)
            
        elif isinstance(item, list) and assert_string(item):
            print key, item
            params[key] = item
    
    return params


def optimize_model(estimator, X_train, y_train, params, scores, folds, verbose, n_jobs):
    clf = None
    for score_name, score_func in scores:
        log.info("Tuning hyper-parameters for %s" % score_name)
        
        log.debug(params)
        log.debug(scores)
        
        clf = GridSearchCV(estimator, params, loss_func=score_func, 
                           cv=folds, verbose=verbose, n_jobs=n_jobs)
        
        clf.fit(X_train, y_train)
        
        log.info("Best parameters set found on development set:")
        log.info(clf.best_params_)
        
    return clf.best_estimator_


def set_learning_method(config, X_train, y_train):
    """
    Instantiates the sklearn's class corresponding to the value set in the 
    configuration file for running the learning method.
    
    TODO: use reflection to instantiate the classes
    
    @param config: configuration object
    @return: an estimator with fit() and predict() methods
    """
    estimator = None
    learning_cfg = config.get("learning", None)
    if learning_cfg:
        p = learning_cfg.get("parameters", None)
        o = learning_cfg.get("optimize", None)
        scorers = \
        set_scorer_functions(learning_cfg.get("scorer", ['mae', 'rmse']))
        method_name = learning_cfg.get("method", None)
        if method_name == "SVR":
            if o:
                tune_params = set_optimization_params(o)
                #X_train = X_train[:, [0, 1]] #gives us column 0 and 1 from the original
                estimator = optimize_model(SVR(), X_train, y_train, 
                                          tune_params, 
                                          scorers, 
                                          o.get("cv", 5),
                                          o.get("verbose", True),
                                          o.get("n_jobs", 1))
                
            elif p:
                estimator = SVR(C=p.get("C", 10),
                                epsilon=p.get('epsilon', 0.01),
                                kernel=p.get('kernel', 'rbf'),
                                degree=p.get('degree', 3),
                                gamma=p.get('gamma', 0.0034),
                                tol=p.get('tol', 1e-3),
                                verbose=False)
            else:
                estimator = SVR()
        
        elif method_name == "SVC":
            if o:
                tune_params = set_optimization_params(o)
                estimator = optimize_model(SVC(), X_train, y_train,
                                           tune_params,
                                           scorers,
                                           o.get('cv', 5),
                                           o.get('verbose', True),
                                           o.get('n_jobs', 1))
                
            elif p:
                estimator = SVC(C=p.get('C', 1.0),
                                kernel=p.get('kernel', 'rbf'), 
                                degree=p.get('degree', 3),
                                gamma=p.get('gamma', 0.0),
                                coef0=p.get('coef0', 0.0),
                                tol=p.get('tol', 1e-3),
                                verbose=p.get('verbose', False))
            else:
                estimator = SVC()
                    
        elif method_name == "LassoCV":
            if p:
                estimator = LassoCV(eps=p.get('eps', 1e-3),
                                    n_alphas=p.get('n_alphas', 100),
                                    normalize=p.get('normalize', False),
                                    precompute=p.get('precompute', 'auto'),
                                    max_iter=p.get('max_iter', 1000),
                                    tol=p.get('tol', 1e-4),
                                    cv=p.get('cv', 10),
                                    verbose=False)
            else:
                estimator = LassoCV()
        
        elif method_name == "LassoLars":
            if o:
                tune_params = set_optimization_params(o)
                estimator = optimize_model(LassoLars(), X_train, y_train, 
                                          tune_params,
                                          scorers,
                                          o.get("cv", 5),
                                          o.get("verbose", True),
                                          o.get("n_jobs", 1))
                
            if p:
                estimator = LassoLars(alpha=p.get('alpha', 1.0),
                                      fit_intercept=p.get('fit_intercept', True),
                                      verbose=p.get('verbose', False),
                                      normalize=p.get('normalize', True),
                                      max_iter=p.get('max_iter', 500),
                                      fit_path=p.get('fit_path', True))
            else:
                estimator = LassoLars()
        
        elif method_name == "LassoLarsCV":
            if p:
                estimator = LassoLarsCV(max_iter=p.get('max_iter', 500),
                                        normalize=p.get('normalize', True),
                                        max_n_alphas=p.get('max_n_alphas', 1000),
                                        n_jobs=p.get('n_jobs', 1),
                                        cv=p.get('cv', 10),
                                        verbose=False)
            else:
                estimator = LassoLarsCV()
        
	elif method_name == "LinearRegression":
            if p:
		estimator = linear_model.LinearRegression()
            else:
		estimator = linear_model.LinearRegression()
	elif method_name == "ExtraTreeClassifier":
            if p:
                estimator = \
                ExtraTreesClassifier(n_estimators=p.get('n_estimators', 10),
                                     max_depth=p.get('max_depth', None),
                                     min_samples_split=p.get('min_samples_split', 1),
                                     min_samples_leaf=p.get('min_samples_leaf', 1),
                                     min_density=p.get('min_density', 1),
                                     max_features=p.get('max_features', 'auto'),
                                     bootstrap=p.get('bootstrap', False),
                                     #compute_importances=p.get('compute_importances', True),
                                     n_jobs=p.get('n_jobs', 1),
                                     random_state=p.get('random_state', None),
                                     # TODO: set verbosity according to global level
                                     verbose=True)
            else:
		estimator = \
                ExtraTreesClassifier(n_estimators=p.get('n_estimators', 10),
                                     max_depth=p.get('max_depth', None),
                                     min_samples_split=p.get('min_samples_split', 1),
                                     min_samples_leaf=p.get('min_samples_leaf', 1),
                                     min_density=p.get('min_density', 1),
                                     max_features=p.get('max_features', 'auto'),
                                     bootstrap=p.get('bootstrap', False),
                                     #compute_importances=p.get('compute_importances', True),
                                     n_jobs=p.get('n_jobs', 1),
                                     random_state=p.get('random_state', None),
                                     # TODO: set verbosity according to global level
                                     verbose=True)
	elif method_name == "ExtraTreesRegressor":
            if p:
		estimator = \
                ExtraTreesRegressor(n_estimators=100, verbose=True)
		'''
                ExtraTreesRegressor(n_estimators=p.get('n_estimators', 10),
                                     max_depth=p.get('max_depth', None),
                                     #min_samples_split=p.get('min_samples_split', 1),
                                     min_samples_split=p.get('min_samples_split', 2),
                                     min_samples_leaf=p.get('min_samples_leaf', 1),
                                     #min_density=p.get('min_density', 1),
                                     max_features=p.get('max_features', 'auto'),
                                     bootstrap=p.get('bootstrap', False),
                                     #compute_importances=p.get('compute_importances', True),
                                     n_jobs=p.get('n_jobs', 1),
                                     random_state=p.get('random_state', None),
                                     # TODO: set verbosity according to global level
                                     verbose=True)
		'''
            else:
		estimator = \
                ExtraTreesRegressor(n_estimators=100, verbose=True)
    return estimator, scorers


def fit_predict(config, X_train, y_train, X_test=None, y_test=None, ref_thd=None):
    '''
    Uses the configuration dictionary settings to train a model using the
    specified training algorithm. If set, also evaluates the trained model 
    in a test set. Additionally, performs feature selection and model parameters
    optimization.
    
    @param config: the configuration dictionary obtained parsing the 
    configuration file.
    @param X_train: the np.array object for the matrix containing the feature
    values for each instance in the training set.
    @param y_train: the np.array object for the response values of each instance
    in the training set.
    @param X_test: the np.array object for the matrix containing the feature
    values for each instance in the test set. Default is None.
    @param y_test: the np.array object for the response values of each instance
    in the test set. Default is None.
    '''
    # sets the selection method
    transformer = set_selection_method(config)

    log.info("X_train dimensions before fit(): %s,%s" % X_train.shape)
    log.info("y_train dimensions before fit(): %s" % y_train.shape)
    
    np.set_printoptions(precision=3)
    log.info("X_train samples before fit(): %s" % X_train)
    log.info("y_train samples before fit(): %s" % y_train)


    # if the system is configured to run feature selection
    # runs it and modifies the datasets to the new dimensions
    if transformer is not None:
        log.info("Running feature selection %s" % str(transformer))
        X_train = transformer.fit_transform(X_train, y_train)
        #X_train = transformer.fit(X_train, y_train)
        ''' code to print out tree check the extratree dir''' 
        #clf = clf.fit(wine.data, wine.target) # train the tree
        #tree_feature_names = ['GB1','GB2','GB5','GB6','TknsInTWiggle']
        #tree_target_names = ['WER']
        # export the learned decision tree
        #print "tree is exporting to pdf"
        #dot_data = tree.export_graphviz(X_train, out_file=None,
        #			 feature_names=tree_feature_names,
        #			 class_names=tree_target_names,
            #			 filled=True, rounded=True,
            #			 special_characters=True)
        #graph = graphviz.Source(dot_data)
        #graph.render("extra_tree") # tree saved to wine.pdf
        #sys.exit()
        log.info('these are the features: ' + str(transformer.feature_importances_))
        log.info("Num Features: %d" % transformer.n_features_)
        #log.info("Selected Features: %s" % transformer.support_)
        #log.info("Feature Ranking: %s" % transformer.ranking_)
        log.info("X_train dimensions after fit_transform(): %s,%s" % X_train.shape)
        log.info("y_train dimensions after fit_transform(): %s" % y_train.shape)
        if X_test is not None:
            X_test = transformer.transform(X_test)
    
    log.info("X_train dimensions after transform(): %s,%s" % X_train.shape)
    log.info("y_train dimensions after transform(): %s" % y_train.shape)
    log.info("X_train samples after transform(): %s" % X_train)
    log.info("y_train samples after transform(): %s" % y_train)
    
    model_file = "model_file.pkl"
    learning_cfg = config.get("learning", None)
    # sets learning algorithm and runs it over the training data
    estimator, scorers = set_learning_method(config, X_train, y_train)
    log.info("Running learning algorithm %s" % str(estimator))
    if learning_cfg:
        l_file = learning_cfg.get("loadfile", None)
    	log.info("reading model file option" + str(l_file))
        if l_file is not None and str(l_file) == "True":
            # load model
    	    log.info("loading model file:" + str(model_file))
            estimator = pickle.load( open( model_file, "rb" ) )
        else:
            estimator.fit(X_train, y_train)
    else:
        estimator.fit(X_train, y_train)
    ### below only svr
    #log.info("dual coeff:" + str(estimator.dual_coef_))
    #log.info("support vectors:" + str(estimator.support_vectors_))
    #log.info("intercept:" + str(estimator.intercept_))
    ### above only svr
    save_feature_pic(estimator,X_train)
    log.info("dumping pickle file:" + str(model_file))
    pickle.dump(estimator, open( model_file, "wb" ))
	
    log.info("X_train dimensions after fit(): %s,%s" % X_train.shape)
    log.info("y_train dimensions after fit(): %s" % y_train.shape)
    log.info("X_train samples after fit(): %s" % X_train)
    log.info("y_train samples after fit(): %s" % y_train)
    
    if (X_test is not None) and (y_test is not None):
        log.info("Predicting unseen data using the trained model...")
        y_hat = estimator.predict(X_test)
        log.info("Saving Scatter Plot...")
        #save_scatter_pic(X_test, y_test, y_hat) # tryign to see the svr
        #log.info("Saving Scatter Mesh...")
        #save_scatter_mesh(X_test, y_test, estimator) # tryign to see the svr
        #test_mesh()# tryign to see the svr
        log.info("Evaluating prediction on the test set...")
        for scorer_name, scorer_func in scorers:
            v = scorer_func(y_test, y_hat)
            log.info("%s = %s" % (scorer_name, v))
        log.info("Customized scores: ")
        try:
            log.info("pearson_corrcoef = %s" % pearson_corrcoef(y_test,  y_hat))
        except:
            pass
        try:
            log.info("Precision score: = %s" % precision_score(y_test, y_hat))
        except:
            pass
        try:
            log.info("Recall score: = %s" % recall_score(y_test, y_hat))
        except:
            pass
        try:
            log.info("F1 score: = %s" % f1_score(y_test, y_hat))
        except:
            pass
        try:
            log.info("MAE: = %s" % mean_absolute_error(y_test, y_hat))
        except:
            pass
        try:
            log.info("RMSE: = %s" % root_mean_squared_error(y_test, y_hat))
        except:
            pass
        try:
            res = classify_report_bin(y_test, y_hat)
            if "N/A" <> res:
                log.info("Classify report bin: = %s" % res)
            else:
                res = classify_report_bin_regression(y_test, y_hat)
                if "N/A" <> res:
                    log.info("Classify report bin regression: = %s" % res)
                else:
                    if ref_thd is None:
                        log.error("No ref thd defined")
                    else:
			log.info("right before ref_thd")
                        refthd = float(ref_thd)
			log.info("right after ref_thd")
                        res = classify_report_regression(y_test, y_hat, refthd)
			log.info("right after classify  ref_thd")
                        log.info("Classify report regression: = %s" % res)
        except Exception, e:
            print e
        with open("predicted.csv", 'w') as _fout:
            for _x,  _y in zip(y_test, y_hat):
                print >> _fout,  "%f\t%f" % (_x,  _y)

def run(config):
    '''
    Runs the main code of the program. Checks for mandatory parameters, opens
    input files and performs the learning steps.
    '''
    # check if the mandatory parameters are set in the config file
    x_train_path = config.get("x_train", None)
    if not x_train_path:
        msg = "'x_train' option not found in the configuration file. \
        The training dataset is mandatory."
        raise Exception(msg)

    y_train_path = config.get("y_train", None)
    if not y_train_path:
        msg = "'y_train' option not found in the configuration file. \
        The training dataset is mandatory."
        raise Exception(msg)
        
    learning = config.get("learning", None)
    if not learning:
        msg = "'learning' option not found. At least one \
        learning method must be set."
        raise Exception(msg)
    
    # checks for the optional parameters
    x_test_path = config.get("x_test", None)
    y_test_path = config.get("y_test", None)

    separator = config.get("separator", DEFAULT_SEP)
    
    labels_path = config.get("labels", None)
        
    scale = config.get("scale", True)

    log.info("Opening input files ...")
    log.info("X_train: %s" % x_train_path)
    log.info("y_train: %s" % y_train_path)
    log.info("X_test: %s" % x_test_path)
    log.info("y_test_path: %s" % y_test_path)

    # open feature and response files    
    X_train, y_train, X_test, y_test, labels = \
    open_datasets(x_train_path, y_train_path, x_test_path,
                  y_test_path, separator, labels_path)

    if scale:
        # preprocess and execute mean removal
        X_train, X_test = scale_datasets(X_train, X_test)

    # fits training data and predicts the test set using the trained model
    y_hat = fit_predict(config, X_train, y_train, X_test, y_test, config.get("ref_thd", None))
    
    
def main(argv=None): # IGNORE:C0111
    '''Command line options.'''
    
    if argv is None:
        argv = sys.argv
    else:
        sys.argv.extend(argv)

    program_name = os.path.basename(sys.argv[0])
    program_version = "v%s" % __version__
    program_build_date = str(__updated__)
    program_version_message = '%%(prog)s %s (%s)' % (program_version, program_build_date)
    program_shortdesc = __import__('__main__').__doc__.split("\n")[1]
    program_license = '''%s

  Created by José de Souza on %s.
  Copyright 2012. All rights reserved.
  
  Licensed under the Apache License 2.0
  http://www.apache.org/licenses/LICENSE-2.0
  
  Distributed on an "AS IS" basis without warranties
  or conditions of any kind, either express or implied.

USAGE
''' % (program_shortdesc, str(__date__))

    try:
        # Setup argument parser
        parser = ArgumentParser(description=program_license, 
                                formatter_class=RawDescriptionHelpFormatter)
        
        parser.add_argument("configuration_file", action="store", 
                            help="path to the configuration file (YAML file).")
        parser.add_argument("-v", "--verbose", dest="verbose", action="count", 
                            help="set verbosity level [default: %(default)s]")
        parser.add_argument('-V', '--version', action='version', 
                            version=program_version_message)

        # Process arguments
        args = parser.parse_args()
        
        cfg_path = args.configuration_file
        
        if args.verbose:
            log.basicConfig(level=log.DEBUG)
        else:
            log.basicConfig(level=log.INFO)
            
        # opens the config file
        config = None
        with open(cfg_path, "r") as cfg_file:
            config = yaml.load(cfg_file.read())
         
        run(config)
        
        
    except KeyboardInterrupt:
        ### handle keyboard interrupt ###
        return 0

if __name__ == "__main__":
    if DEBUG:
        sys.argv.append("-v")

    sys.exit(main())
